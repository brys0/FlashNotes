package com.brys.flashnotes.api.controllers

import com.brys.flashnotes.api.Cache
import com.brys.flashnotes.api.CardGroup
import com.brys.flashnotes.api.Snowflake
import com.brys.flashnotes.api.User
import io.javalin.http.Context
import io.javalin.http.util.NaiveRateLimit
import io.javalin.plugin.openapi.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit

class CardGroupController(private val cache: Cache, private val snowflake: Snowflake, private val allowlist: List<String>?, private val blocklist: List<String>?) {
    @OpenApi(
        path = "/create",
        method = HttpMethod.POST,
        requestBody = OpenApiRequestBody(content = arrayOf(OpenApiContent(from = CreateCardGroup::class))),
        responses = [
            OpenApiResponse("201", content = [OpenApiContent(CardGroup::class, type = "application/json")]),
            OpenApiResponse("400"),
            OpenApiResponse("204"),
        ],
        queryParams = [OpenApiParam(
            "groupid",
            String::class,
            "The group id to update (if any)",
            required = false,
            allowEmptyValue = false
        )],
        summary = "Create Post (With User Auth)",
        description = "Creates a post with the users auth",
        tags = ["User", "Post"],
        operationId = "createPost"
    )
    fun createGroup(ctx: Context) {
        val cardGroup = ctx.bodyAsClass<CreateCardGroup>()
        val cardGroupId = ctx.queryParam("groupid")?.toLong()
        val user = requireAuth(cache, ctx, allowlist, blocklist)
        if (user != null) {
            if (cardGroupId == null) {
                val newGroup = CardGroup(
                    cache,
                    cardGroup.title,
                    cardGroup.sentences,
                    cardGroup.words,
                    cardGroup.tags,
                    snowflake.nextId(),
                    user.identifier
                )
                newGroup.tags.forEach { tag ->
                    val existingIds = cache.tags[tag]
                    if (existingIds == null) {
                        cache.tags[tag] = mutableListOf(newGroup.identifier)
                    } else {
                        existingIds.add(newGroup.identifier)
                        cache.tags[tag] = existingIds
                    }
                }
                newGroup.save(true)
                ctx.status(201).json(newGroup)
                return
            } else {
                val existingGroup = cache.groups.values.find { g -> g.identifier == cardGroupId }
                if (existingGroup == null) {
                    ctx.status(400)
                    return
                } else {
                    existingGroup.title = cardGroup.title
                    existingGroup.tags = cardGroup.tags
                    existingGroup.sentences = cardGroup.sentences
                    existingGroup.words = cardGroup.words
                    existingGroup.save(true)
                    existingGroup.tags.forEach { tag ->
                        val existingIds = cache.tags[tag]
                        if (existingIds == null) {
                            cache.tags[tag] = mutableListOf(existingGroup.identifier)
                        } else {
                            existingIds.add(existingGroup.identifier)
                            cache.tags[tag] = existingIds
                        }
                    }
                    ctx.status(204)
                    return
                }
            }
        }
    }

    @OpenApi(
        path = "/mygroups",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("204"), OpenApiResponse(
            "200",
            content = [OpenApiContent(CardGroup::class, type = "application/json", isArray = true)]
        )],
        summary = "Get self groups",
        description = "Gets all users posts",
        tags = ["User", "Post"],
        operationId = "getSelfPosts"
    )
    fun getMyGroups(ctx: Context) {
        val user = requireAuth(cache, ctx, allowlist, blocklist)
        if (user != null) {
            val existing = mutableListOf<CardGroup>()
            cache.groups.values.forEach {
                if (it.created_from == user.identifier) {
                    existing.add(it)
                }
            }
            if (existing.isNotEmpty()) {
                ctx.status(200).json(existing)
                return
            } else {
                ctx.status(204)
                return
            }
        }
    }

    @OpenApi(
        path = "/tags",
        method = HttpMethod.GET,
        responses = [
            OpenApiResponse(
                "201",
                content = [OpenApiContent(String::class, type = "application/json", isArray = true)]
            ),
        ],
        summary = "Get All Group Tags",
        description = "Gets all current group tags",
        tags = ["Post"],
        operationId = "getTags"
    )
    fun getTags(ctx: Context) {
        val useableJsonMap = mutableMapOf<String, MutableList<String>>()
        cache.tags.forEach { (k, v) ->
            val convertedValues = mutableListOf<String>()
            v.forEach { vv -> convertedValues.add(vv.toString()) }
            useableJsonMap[k] = convertedValues
        }
        ctx.status(200).json(useableJsonMap)
    }

    @OpenApi(
        path = "/filter",
        method = HttpMethod.GET,
        responses = [
            OpenApiResponse(
                "200",
                content = [OpenApiContent(CardGroup::class, type = "application/json", isArray = true)]
            ),
        ],
        queryParams = [OpenApiParam(
            "filterBy",
            String::class,
            "The key to filter by (Tag/Title)",
            required = false,
            allowEmptyValue = false
        ),
            OpenApiParam(
                "value",
                String::class,
                "The value of the Tag or Title",
                required = false,
                allowEmptyValue = false
            )],
        summary = "Filter Cards",
        description = "Filters card based on given tag or title",
        tags = ["Post"],
        operationId = "filterCards"
    )
    fun filterCards(ctx: Context) {
        NaiveRateLimit.requestPerTimeUnit(ctx, 80, TimeUnit.MINUTES)
        val type = ctx.queryParam("filterBy").toString().lowercase(Locale.getDefault())
        val value = ctx.queryParam("value").toString()
        ctx.header("X-BeforeRateLimit", 80.toString())
        when (type) {
            "tag" -> {
                val cardIds = cache.tags[value]
                val cards = mutableListOf<CardGroup>()
                cardIds?.forEach { card ->
                    cache.groups[card]?.let { it.words.clear(); it.sentences.clear(); cards.add(it); }
                }
                ctx.status(200).json(cards)
                return
            }
            "title" -> {
                val filteredGroup = mutableListOf<CardGroup>()
                cache.groups.values.forEach { card ->
                    if (card.title.contains(value)) {
                        filteredGroup.add(card)
                        card.words.clear()
                        card.sentences.clear()
                    }
                }
                ctx.status(200).json(filteredGroup)
            }
        }
    }

    @OpenApi(
        path = "/browse",
        method = HttpMethod.GET,
        responses = [OpenApiResponse(
            "200",
            content = arrayOf(OpenApiContent(CardGroup::class, type = "application/json", isArray = true))
        )],
        summary = "Browse cards",
        description = "Gets a random 20 cards from the cache",
        tags = ["Post"],
        operationId = "browseCards"
    )
    fun browseCards(ctx: Context) {
        ctx.json(cache.groups.values.chunked(20)[0]).status(200)
    }

    fun getCardGroup(ctx: Context) {
        val key = ctx.queryParam("id")?.toLong()
        if (key == null) {
            ctx.status(400)
            return
        } else {
            cache.groups[key]?.let { ctx.status(200).json(it) }
        }

    }
    data class CreateCardGroup(
        var title: String = "Example Group",
        var sentences: MutableMap<String, String> = mutableMapOf(),
        var words: MutableMap<String, String> = mutableMapOf(),
        var tags: MutableList<String> = mutableListOf(),
    )
}

fun requireAuth(cache: Cache, ctx: Context, allowlist: List<String>? = null, blocklist: List<String>? = null): User? {
    val auth = ctx.header("Authorization")
    val whiteListActive = allowlist?.isNotEmpty()
    val blackListActive = blocklist?.isNotEmpty()
    cache.users.values.forEach {
        println("User ${it.name} (${it.identifier}) - ${it.auth} Compare $auth")
    }
    if (auth == null) {
        ctx.status(401).json(UserAuthFail())
    } else {
        val user = cache.users.values.find { user -> user.auth == auth }
        if (whiteListActive == true && !allowlist.contains(user?.email)) {
            ctx.status(403).json(
                VerifyGoogleController.Companion.NotInWhiteList(
                    "USER_NOT_IN_LIST",
                    "This server is using a allowlist, and you're email isn't in it"
                )
            )
            return null
        } else if (blackListActive == true && blocklist.contains(user?.email)) {
            ctx.status(403).json(
                VerifyGoogleController.Companion.InBlackList(
                    "USER_IN_LIST",
                    "This server is using a blocklist, and you're in it"
                )
            )
            return null
        }
        return if (user == null) {
            ctx.status(401).json(UserAuthFail())
            null
        } else {
            user
        }
    }
    return null
}

class UserAuthFail {
    val code: String
        get() = "AUTH_USER_FAIL"
    val message: String
        get() = "Your authorization is incorrect or missing"
}
package com.brys.flashnotes.api.controllers

import com.brys.flashnotes.api.Cache
import com.brys.flashnotes.api.CardGroup
import com.brys.flashnotes.api.Snowflake
import com.brys.flashnotes.api.User
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

class CardGroupController(private val cache: Cache, private val snowflake: Snowflake) {
    @OpenApi(
        path = "/create",
        method = HttpMethod.POST,
        requestBody = OpenApiRequestBody(content = arrayOf(OpenApiContent(from = CreateCardGroup::class))),
        responses = [
            OpenApiResponse("201", content = [OpenApiContent(CardGroup::class, type = "application/json")]),
            OpenApiResponse("400"),
            OpenApiResponse("204"),
        ],
        queryParams = [OpenApiParam("groupid", String::class, "The group id to update (if any)", required = false, allowEmptyValue = false)],
        summary = "Create Post (With User Auth)",
        description = "Creates a post with the users auth",
        tags = ["User","Post"],
        operationId = "createPost"
    )
    fun createGroup(ctx: Context) {
        val cardGroup = ctx.bodyAsClass<CreateCardGroup>()
        val cardGroupId = ctx.queryParam("groupid")?.toLong()
        val user = requireAuth(cache, ctx)
        if (user != null) {
            if (cardGroupId == null) {
                val newGroup = CardGroup(cache, cardGroup.title, cardGroup.sentences, cardGroup.words, cardGroup.tags, snowflake.nextId(), user.identifier)
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
                val existingGroup = cache.groups.values.find {g -> g.identifier == cardGroupId }
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
        path = "/tags",
        method = HttpMethod.GET,
        responses = [
            OpenApiResponse("201", content = [OpenApiContent(String::class, type = "application/json", isArray = true)]),
        ],
        summary = "Get All Group Tags",
        description = "Gets all current group tags",
        tags = ["Post"],
        operationId = "getTags"
    )
    fun getTags(ctx: Context) {
        ctx.status(200).json(cache.tags)
    }


    fun filterCards(ctx: Context) {
        val type = ctx.queryParam("filterBy").toString()
        val value = ctx.queryParam("value").toString()
        when (type) {
            "tag" -> {
                val cardIds = cache.tags[value]
                val cards = mutableListOf<CardGroup>()
                cardIds?.forEach { card ->
                    cache.groups[card]?.let { cards.add(it) }
                }
                ctx.status(200).json(cards)
                return
            }
            "title" -> {

            }
        }
    }
    data class CreateCardGroup(
        var title: String = "Example Group",
        var sentences: MutableMap<String, String> = mutableMapOf(),
        var words: MutableMap<String, String> = mutableMapOf(),
        var tags: MutableList<String> = mutableListOf(),
    )
}
fun requireAuth(cache: Cache, ctx: Context): User? {
    val auth = ctx.header("Authorization")
    cache.users.values.forEach {
        println("User ${it.name} (${it.identifier}) - ${it.auth} Compare $auth")
    }
    if (auth == null) {
        ctx.status(401).json(UserAuthFail())
    }  else {
        val user = cache.users.values.find { user -> user.auth == auth }
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
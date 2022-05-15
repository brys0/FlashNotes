package com.brys.flashnotes.api.controllers

import com.brys.flashnotes.api.Cache
import com.brys.flashnotes.api.Snowflake
import com.brys.flashnotes.api.User
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

class VerifyGoogleController(private val gid: String, private val cache: Cache, private val snowflake: Snowflake) {
    private val verifier: GoogleIdTokenVerifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
        .setAudience(listOf(gid))
        .build()
    @OpenApi(
        path = "/verify-token",
        method = HttpMethod.POST,
        requestBody = OpenApiRequestBody(content = arrayOf(OpenApiContent(from = VerifyPayload::class))),
        responses = [
            OpenApiResponse("201", content = [OpenApiContent(User::class, type = "application/json")]),
            OpenApiResponse("200", content = [OpenApiContent(User::class, type = "application/json")]),
            OpenApiResponse("400", content = [OpenApiContent(FailedToAuthenticate::class, type = "application/json")])
        ],
        summary = "Verify Token (Create User)",
        description = "Verifies the Google token then creates a user in the database",
        tags = ["User"],
        operationId = "verifyToken"
    )
    fun verifyToken(ctx: Context) {
        val body = ctx.bodyAsClass<VerifyPayload>()
        try {
            val idToken = verifier.verify(body.credential)
            if (idToken != null) {
                val payload = idToken.payload
                val existing = cache.users.values.find { u -> u.email == payload.email }
                if (existing == null) {
                    val newSnowflake = snowflake.nextId()
                    val newUser = User(cache, newSnowflake, payload.email, payload["name"].toString())
                    newUser.jsonId = newSnowflake.toString()
                    cache.saveOrReplaceUser(newUser)
                    ctx.json(201).json(newUser)
                    return
                } else {
                    existing.name = payload["name"].toString()
                    existing.save(true)
                    ctx.json(200).json(existing)
                }

            }
        } catch (e: Exception) {
            ctx.json(400).json(FailedToAuthenticate("FAILED_TO_VERIFY", e.message))
        }
    }
    companion object {
        data class VerifyPayload(
            val clientId: String,
            val credential: String,
            val select_by: String
        )

        data class FailedToAuthenticate(
            val code: String,
            val message: String?
        )
    }
}


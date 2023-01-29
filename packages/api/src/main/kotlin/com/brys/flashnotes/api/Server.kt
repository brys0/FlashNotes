package com.brys.flashnotes.api

import com.brys.flashnotes.api.controllers.CardGroupController
import com.brys.flashnotes.api.controllers.VerifyGoogleController
import com.brys.flashnotes.api.revised.Authentication
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.openapi.OpenApiInfo
import io.javalin.openapi.plugin.OpenApiConfiguration
import io.javalin.openapi.plugin.OpenApiPlugin
import io.javalin.plugin.bundled.RouteOverviewPlugin
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


fun main() {
    val id = System.getenv("GOOGLE_CLIENT_ID")
    val authentication = Authentication()
    val token = authentication.createToken(56000)
    println("JWT: $token")
    println("Validation: ${authentication.validateToken(token!!)}")
    val port = System.getenv("PORT").toInt()
    val allowlist = System.getenv("ALLOWLIST").split(",")
    val blocklist = System.getenv("BLOCKLIST").split(",")
    val dateFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault())
    val timeFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("hh:mm:ss").withZone(ZoneId.systemDefault())
    val async: ExecutorService = Executors.newCachedThreadPool()
    val snowflake = Snowflake()
    val mongoDatabase = MongoDatabase()
    val cache = Cache(async, mongoDatabase, snowflake)
    val javalin = Javalin.create { javalinConfig ->
        javalinConfig.plugins.register(RouteOverviewPlugin("/routes"))
        javalinConfig.plugins.register(getConfiguredOpenApiPlugin())
        javalinConfig.plugins.enableCors { corsContainer -> corsContainer.add { it.anyHost() } }
    }
    val googleController = VerifyGoogleController(id, cache, snowflake, allowlist, blocklist)
    val cardGroupController = CardGroupController(cache, snowflake, allowlist, blocklist)


    javalin.routes {
        post("/verify-token", googleController::verifyToken)
        post("/create", cardGroupController::createGroup)
        get("/tags", cardGroupController::getTags)
        get("/filter", cardGroupController::filterCards)
        get("/browse", cardGroupController::browseCards)
        get("/mygroups", cardGroupController::getMyGroups)
        get("/card", cardGroupController::getCardGroup)
    }
    javalin.start(port)
}

fun getConfiguredOpenApiPlugin(): OpenApiPlugin {
    val config = OpenApiConfiguration()
    config.info = OpenApiInfo().apply {
        title = "FlashNotes"
        summary = "A note taking API"
        description = "Flash notes is a minimal, lightweight service to allow people to learn new languages"
        version = "0.0.2"
    }
    return OpenApiPlugin(config)
}



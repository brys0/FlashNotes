package com.brys.flashnotes.api

import com.brys.flashnotes.api.controllers.CardGroupController
import com.brys.flashnotes.api.controllers.VerifyGoogleController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.core.util.RouteOverviewPlugin
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


fun main() {
    val id = System.getenv("GOOGLE_CLIENT_ID")
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
    val javalin = Javalin.create {
        it.enableCorsForAllOrigins()
        it.registerPlugin(RouteOverviewPlugin("/routes"))
        it.registerPlugin(getConfiguredOpenApiPlugin())
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
fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
    OpenApiOptions(
        Info().apply {
            version("0.0.1")
            description("Chatty API")
        }
    ).activateAnnotationScanningFor("com.brys.flashnotes.api.controllers").apply {
        path("/swagger-docs") // endpoint for OpenAPI json
        swagger(SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
        reDoc(ReDocOptions("/redoc")) // endpoint for redoc
    }
)



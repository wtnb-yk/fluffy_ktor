package com.fluffy.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        openAPI(path="openapi", swaggerFile = "_build/openapi.yaml")
        swaggerUI(path = "swagger", swaggerFile = "_build/openapi.yaml")
    }
}

package no.nav.pensjonbrevdata

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.healthRoute() {
    get("/isAlive") {
        call.respondText("Alive!", ContentType.Text.Plain, HttpStatusCode.OK)
    }

    get("/isReady") {
        call.respondText("Ready!", ContentType.Text.Plain, HttpStatusCode.OK)
    }
}
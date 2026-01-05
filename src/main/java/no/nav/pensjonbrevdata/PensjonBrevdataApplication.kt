package no.nav.pensjonbrevdata

import com.fasterxml.jackson.databind.DeserializationFeature
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.callid.generate
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.routing
import no.nav.pensjonbrevdata.Metrics.configureMetrics
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapperImpl
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("PensjonBrevdataApplication")

fun main() {
    try {
        val config = ConfigFactory.load().getConfig("brevmetadata")
        embeddedServer(
            Netty,
            configure = {
                connectors.add(EngineConnectorBuilder().apply {
                    host = "0.0.0.0"
                    port = config.getInt("port")
                })
            },
            ) { configureApp() }
            .start(wait = true)
    }  catch (e: Exception) {
        logger.error(e.message, e)
        throw e
    }
}

fun Application.configureApp() {
    install(CallLogging) {
        callIdMdc("x_correlationId")
        disableDefaultColors()
        val ignorePaths = setOf("/api/internal/isAlive", "/api/internal/isReady", "/metrics")
        filter {
            !ignorePaths.contains(it.request.path())
        }
    }
    install(CallId) {
        header("X-Request-ID")
        generate()
        verify { it.isNotEmpty() }
    }

    install(StatusPages) {
        exception<Exception> { call, cause ->
            logger.error(cause.message, cause)
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Ukjent intern feil")
        }
    }

    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }
    install(IgnoreTrailingSlash)

    val brevdataMapper = BrevdataMapperImpl()
    val sakBrevMapper = SakBrevMapper()
    val provider = BrevdataProvider(brevdataMapper, sakBrevMapper)

    routing {
        routes(provider)
    }
    configureMetrics()
}


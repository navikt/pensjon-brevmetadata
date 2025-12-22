package no.nav.pensjonbrevdata

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.BadRequestException
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
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapperImpl
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import org.slf4j.LoggerFactory
import tools.jackson.core.JacksonException

private val logger = LoggerFactory.getLogger("PensjonBrevdataApplication")

fun main() {
    try {
        embeddedServer(
            Netty,
            configure = {
                connectors.add(EngineConnectorBuilder().apply {
                    host = "0.0.0.0"
                    port = 8085
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
        val ignorePaths = setOf("/isAlive", "/isReady", "/metrics")
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
        exception<JacksonException> { call, cause ->
            logger.info("Bad request, kunne ikke deserialisere json")
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = cause.message ?: "Failed to deserialize json body: unknown cause"
            )
        }
        exception<BadRequestException> { call, cause ->
            if (cause.cause is JsonConvertException) {
                logger.info(cause.message, cause)
                call.respond(
                    HttpStatusCode.BadRequest,
                    cause.cause?.message ?: "Failed to deserialize json body: unknown reason"
                )
            } else {
                logger.info("Bad request, men ikke knytta til deserialisering")
                call.respond(HttpStatusCode.BadRequest, cause.message ?: "Bad request exception")
            }
        }
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

    configureRouting(provider)
    configureMetrics()
}

fun Application.configureRouting(provider: BrevdataProvider) {
    routing {
        routes(provider)
    }
}
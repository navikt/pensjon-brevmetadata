package no.nav.pensjonbrevdata

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication

fun testBrevmetadataApp(
    block: suspend ApplicationTestBuilder.(client: HttpClient) -> Unit,
): Unit = testApplication {
    val client = createClient {
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
    application.configureApp()
    block(client)
}
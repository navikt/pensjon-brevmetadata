package no.nav.pensjonbrevdata

import io.prometheus.client.exporter.common.TextFormat
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONParser
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Locale

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PensjonBrevdataApplicationTests {
    @LocalServerPort
    private val port = 0

    @Test
    fun contextLoads() {
    }

    @Test
    @Throws(IOException::class, InterruptedException::class, JSONException::class)
    fun callForAP_ENDR_OPPTJ_MAN() {
        val response = HttpClient.newHttpClient()
            .send<String?>(
                HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/brevdata/brevForBrevkode/AP_ENDR_OPPTJ_MAN"))
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            )
        JSONParser.parseJSON(response.body())
        Assertions.assertNotNull(response.body())
        val status = HttpStatus.resolve(response.statusCode())
        Assertions.assertNotNull(status)
        Assertions.assertTrue(status!!.is2xxSuccessful())
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun includeXsdShouldOnlyAffectPerRequest() {
        val expected = getBrevdataForSaktypeResponse("KRIGSP", false).body()
        getBrevdataForSaktypeResponse("KRIGSP", true).body()
        val actual = getBrevdataForSaktypeResponse("KRIGSP", false).body()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun shouldReturnAsPlainTextForPrometheus() {
        val response: HttpResponse<*> = HttpClient.newHttpClient()
            .send<String?>(
                HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/internal/prometheus"))
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            )

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode())
        Assertions.assertEquals(
            StringUtils.deleteWhitespace(TextFormat.CONTENT_TYPE_004), StringUtils.deleteWhitespace(
                response.headers().firstValue("Content-Type").get().lowercase(
                    Locale.getDefault()
                )
            )
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun shouldMatchTrailingSlash() {
        val response: HttpResponse<*> = HttpClient.newHttpClient()
            .send<String?>(
                HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/brevdata/allBrev/"))
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            )

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode())
        Assertions.assertEquals(
            MediaType.APPLICATION_JSON_VALUE, StringUtils.deleteWhitespace(
                response.headers().firstValue("Content-Type").get().lowercase(
                    Locale.getDefault()
                )
            )
        )
    }

    @Throws(IOException::class, InterruptedException::class)
    private fun getBrevdataForSaktypeResponse(brevkode: String?, includeXsd: Boolean): HttpResponse<String?> {
        return HttpClient.newHttpClient().send<String?>(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/brevdata/brevdataForSaktype/" + brevkode + "?includeXsd=" + includeXsd))
                .build(), HttpResponse.BodyHandlers.ofString()
        )
    }
}

package no.nav.pensjonbrevdata

import org.json.JSONArray
import org.json.JSONException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KomponentTest {
    @LocalServerPort
    private val port = 0

    @ParameterizedTest(name = "{0}")
    @MethodSource("brevkoder")
    fun testGetSprakForBrevkode(brevkode: String) {
        testEndpoint("sprakForBrevkode", brevkode, "brevkode")
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("brevkoder")
    fun testGetBrevForBrevkode(brevkode: String) {
        testEndpoint("brevForBrevkode", brevkode, "brevkode")
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sakstyper")
    fun testGetBrevdataForSaktype(sakstype: String) {
        testEndpoint("brevdataForSaktype", sakstype, "sakstype", false)
        testEndpoint("brevdataForSaktype", sakstype, "sakstype", true)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sakstyper")
    fun testGetBrevkoderForSaktype(sakstype: String) {
        testEndpoint("brevkoderForSaktype", sakstype, "sakstype")
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("brevkoderIBrevSystem")
    fun testGetBrevKeyForBrevkodeIBrevsystem(brevkodeIBrevsystem: String) {
        testEndpoint("brevKeyForBrevkodeIBrevsystem", brevkodeIBrevsystem, "brevkodeIBrevsystem")
    }

    @Test
    fun testGetAllBrev() {
        testGetAllBrev(false)
        testGetAllBrev(true)
    }

    @Test
    fun alleBrevkoderErRepresentertITest() {
        HttpClient.newHttpClient().use { client ->
            Assertions.assertEquals(
                brevkoder().size, JSONArray(
                    client.send<String>(
                        HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:" + port + "/api/brevdata/allBrev?includeXsd=false"))
                            .build(), HttpResponse.BodyHandlers.ofString()
                    ).body()
                ).length(),
                "Ny brevkode er lagt til eller fjernet uten at KomponentTest.brevkoder() er oppdatert."
            )
        }
    }

    private fun testGetAllBrev(includeXsd: Boolean) {
        HttpClient.newHttpClient().use { client ->
            val resp: HttpResponse<String> = client.send<String>(
                HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/brevdata/allBrev?includeXsd=" + includeXsd))
                    .build(), HttpResponse.BodyHandlers.ofString()
            )
            Assertions.assertEquals(200, resp.statusCode(), "Feil i respons til allBrev med includeXsd " + includeXsd)
            val expected = loadResult("allBrev", "" + includeXsd, null)
            val actual = resp.body()
            if (!(expected.isEmpty() && actual.isEmpty())) JSONAssert.assertEquals(
                "Feil i respons til allBrev med includeXsd " + includeXsd + "\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                expected, actual, true
            )
        }
    }

    private fun testEndpoint(endpoint: String, kode: String, kodeDesc: String, includeXsd: Boolean? = null) {
        try {
            HttpClient.newHttpClient().use { client ->
                val resp: HttpResponse<String> = client.send<String>(
                    HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/api/brevdata/" + endpoint + "/" + kode + (if (includeXsd != null) "?includeXsd=" + includeXsd else "")))
                        .build(), HttpResponse.BodyHandlers.ofString()
                )
                Assertions.assertEquals(200, resp.statusCode(), "Feil i respons til " + kodeDesc + " " + kode)
                val expected = loadResult(endpoint, kode, includeXsd)
                val actual = resp.body()
                if (expected.isEmpty() || actual.isEmpty()) {
                    Assertions.assertEquals(expected, actual, "Feil i " + kodeDesc + " " + kode)
                } else {
                    JSONAssert.assertEquals(
                        "Feil i respons til " + kodeDesc + " " + kode + "\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                        expected, actual, true
                    )
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } catch (e: JSONException) {
            throw RuntimeException(e)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    private fun loadResult(endpoint: String, brevkode: String, includeXsd: Boolean?): String {
        val url =
            javaClass.getResource("/" + endpoint + "/" + (if (includeXsd != null) includeXsd.toString() + "/" else "") + brevkode)
        if (url == null) throw RuntimeException(endpoint + "-resultat ikke funnet for kode: " + brevkode)
        return Files.readString(Path.of(url.toURI()))
    }

    companion object {
        @JvmStatic
        private fun brevkoder(): List<String> = TestDataHolder.brevkoder()

        @JvmStatic
        private fun sakstyper(): List<String> = TestDataHolder.sakstyper()

        @JvmStatic
        private fun brevkoderIBrevSystem(): List<String> = TestDataHolder.brevkoderIBrevSystem()
    }
}

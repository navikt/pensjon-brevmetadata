package no.nav.pensjonbrevdata

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.skyscreamer.jsonassert.JSONAssert
import java.nio.file.Files
import java.nio.file.Path

class KomponentTest {

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
        testBrevmetadataApp { client ->
            val asText = client.get("/api/brevdata/allBrev?includeXsd=false").bodyAsText()
            val read = jacksonObjectMapper().readValue<List<Any>>(asText)
            Assertions.assertEquals(brevkoder().size, read.size,
                "Ny brevkode er lagt til eller fjernet uten at KomponentTest.brevkoder() er oppdatert."
            )
        }
    }

    private fun testGetAllBrev(includeXsd: Boolean) {
        testBrevmetadataApp { client ->
            val resp = client.get("api/brevdata/allBrev?includeXsd=$includeXsd")
            Assertions.assertEquals(HttpStatusCode.OK, resp.status,
                "Feil i respons til allBrev med includeXsd $includeXsd"
            )
            val expected = loadResult("allBrev", "" + includeXsd, null)
            val actual = resp.body<String>()
            if (!(expected.isEmpty() && actual.isEmpty())) JSONAssert.assertEquals(
                "Feil i respons til allBrev med includeXsd $includeXsd\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                expected, actual, true
            )
        }
    }

    private fun testEndpoint(endpoint: String, kode: String, kodeDesc: String, includeXsd: Boolean? = null) {
            testBrevmetadataApp { client ->
                val resp = client.get("/api/brevdata/" + endpoint + "/" + kode + (if (includeXsd != null) "?includeXsd=$includeXsd" else ""))
                Assertions.assertEquals(HttpStatusCode.OK, resp.status, "Feil i respons til $kodeDesc $kode")
                val expected = loadResult(endpoint, kode, includeXsd)
                val actual = resp.body<String>()
                if (expected.isEmpty() || actual.isEmpty()) {
                    Assertions.assertEquals(expected, actual, "Feil i $kodeDesc $kode")
                } else {
                    JSONAssert.assertEquals(
                        "Feil i respons til $kodeDesc $kode\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt." +
                                "expected.length: ${expected.length}, actual.length: ${actual.length}",
                        expected, actual, false
                    )
                }
            }
    }

    private fun loadResult(endpoint: String, brevkode: String, includeXsd: Boolean?): String {
        val url =
            javaClass.getResource("/" + endpoint + "/" + (if (includeXsd != null) "$includeXsd/" else "") + brevkode)
        if (url == null) throw RuntimeException("$endpoint-resultat ikke funnet for kode: $brevkode")
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

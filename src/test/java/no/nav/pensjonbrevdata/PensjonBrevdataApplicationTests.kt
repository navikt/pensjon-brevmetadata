package no.nav.pensjonbrevdata

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONParser

internal class PensjonBrevdataApplicationTests {

    @Test
    fun callForAP_ENDR_OPPTJ_MAN() {
        testBrevmetadataApp { client ->
            val response = client.get("/api/brevdata/brevForBrevkode/AP_ENDR_OPPTJ_MAN")
            val s = response.body<String>()
            JSONParser.parseJSON(s)
            Assertions.assertNotNull(response.body())
            Assertions.assertTrue(response.status.isSuccess())
        }
    }

    @Test
    fun includeXsdShouldOnlyAffectPerRequest() {
        testBrevmetadataApp { client ->
            val expected = client.get("/api/brevdata/brevdataForSaktype/KRIGSP?includeXsd=false").body<String>()
            client.get("/api/brevdata/brevdataForSaktype/KRIGSP?includeXsd=true").body<String>()
            val actual = client.get("/api/brevdata/brevdataForSaktype/KRIGSP?includeXsd=false").body<String>()
            Assertions.assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldMatchTrailingSlash() {
        testBrevmetadataApp { client ->
            Assertions.assertEquals(HttpStatusCode.OK, client.get("/api/brevdata/allBrev").status)
            Assertions.assertEquals(HttpStatusCode.OK, client.get("/api/brevdata/allBrev/").status)
        }
    }
}

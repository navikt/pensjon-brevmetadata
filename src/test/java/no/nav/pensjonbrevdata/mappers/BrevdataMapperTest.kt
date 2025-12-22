package no.nav.pensjonbrevdata.mappers

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BrevdataMapperTest {
    @Test
    fun shouldThrowIllegalArgumentExceptionWhenUnknownBrevkode() {
        val invalidBrevkode = "UGYLDIG_BREVKODE"
        val mapper = BrevdataMapper()

        assertThrows<IllegalArgumentException> {
            mapper.map(invalidBrevkode)
        }
    }

    @Test
    fun test_TestShowAPAvslAutoWhenUsingKeyAP_AVSLAG_AUTO() {
        val testBrev = "AP_AVSLAG_AUTO"

        val mapper = BrevdataMapper()

        val brev = mapper.map(testBrev)

        Assertions.assertNotNull(brev)
        Assertions.assertEquals("AP_AVSL_AUTO", brev!!.brevkodeIBrevsystem)
    }

    @Test
    fun test_TestShowAPAvslAutoWhenUsingKeyAP_AVSL_AUTO() {
        val testBrev = "AP_AVSL_AUTO"

        val mapper = BrevdataMapper()

        val brev = mapper.map(testBrev)

        Assertions.assertNotNull(brev)
        Assertions.assertEquals("AP_AVSL_AUTO", brev!!.brevkodeIBrevsystem)
    }

    @Test
    fun test_TestShowAPAvslAutoWhenUsingKeyPE_AP_04_210() {
        val testBrev = "PE_AP_04_210"

        val mapper = BrevdataMapper()

        assertThrows<IllegalArgumentException> {
            mapper.map(testBrev)
        }
    }
}
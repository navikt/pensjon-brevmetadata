package no.nav.pensjonbrevdata

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class BrevdataEndpointTest {
    @MockK
    private lateinit var brevdataProviderMock: BrevdataProvider

    private lateinit var endpoint: BrevdataEndpoint

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        endpoint = BrevdataEndpoint(brevdataProviderMock)
    }

    @Test
    fun shouldRespondWithBadRequestWhenGetBrevForBrevkodeAndUnknownBrevkode() {
        every { brevdataProviderMock.getBrevForBrevkode(any()) } throws IllegalArgumentException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevForBrevkode("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.BAD_REQUEST))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndRuntimeException() {
        every { brevdataProviderMock.getBrevForBrevkode(any()) } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevForBrevkode("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndIOException() {
        every { brevdataProviderMock.getBrevForBrevkode(any()) } throws RuntimeException()


        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevForBrevkode("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithBadRequestWhenGetBrevdataForSaktypeAndUnknownSaktype() {
        every { brevdataProviderMock.getBrevdataForSaktype("Test") } throws IllegalArgumentException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevdataForSaktype("Test", false) }

        assertThat(thrown.statusCode, `is`(HttpStatus.BAD_REQUEST))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndIOException() {
        every { brevdataProviderMock.getBrevdataForSaktype("Test") } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevdataForSaktype("Test", true) }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndRuntimeException() {
        every { brevdataProviderMock.getBrevdataForSaktype("Test") } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevdataForSaktype("Test", true) }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetAllBrevAndRuntimeException() {
        every { brevdataProviderMock.allBrev } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getAllBrev(false) }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetAllBrevAndIoException() {
        every { brevdataProviderMock.allBrev } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getAllBrev(true) }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetBrevkeyForBrevkodeAndRuntimeException() {
        every { brevdataProviderMock.getBrevKeysForBrevkodeIBrevsystem(any()) } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevKeyForBrevkodeIBrevsystem("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetBrevkoderForSaktypeAndRuntimeException() {
        every { brevdataProviderMock.getBrevkoderForSaktype(any()) } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevkoderForSaktype("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun shouldRespondWithBadRequestWhenGetBrevkoderForSaktypeAndUnknownSaktype() {
        every { brevdataProviderMock.getBrevkoderForSaktype(any()) } throws IllegalArgumentException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getBrevkoderForSaktype("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.BAD_REQUEST))
    }

    @Test
    fun shouldRespondWithBadRequestWhenGetSprakForBrevkodeAndUnknownBrevkode() {
        every { brevdataProviderMock.getSprakForBrevkode(any()) } throws IllegalArgumentException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getSprakForBrevkode("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.BAD_REQUEST))
    }

    @Test
    fun shouldRespondWithInternalServerErrorWhenGetSprakForBrevkodeAndRuntimeException() {
        every { brevdataProviderMock.getSprakForBrevkode(any()) } throws RuntimeException()

        val thrown = Assertions.assertThrows(
            ResponseStatusException::class.java
        ) { endpoint.getSprakForBrevkode("Test") }

        assertThat(thrown.statusCode, `is`(HttpStatus.INTERNAL_SERVER_ERROR))
    }
}
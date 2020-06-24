package no.nav.pensjonbrevdata;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import no.finn.unleash.FakeUnleash;

import no.nav.pensjonbrevdata.unleash.UnleashProvider;

@ExtendWith(MockitoExtension.class)
public class BrevdataEndpointTest {


    @Mock
    private BrevdataProvider brevdataProviderMock;

    private BrevdataEndpoint endpoint;

    @BeforeAll
    static public void setupForAll() {
        UnleashProvider.initialize(new FakeUnleash());
    }

    @BeforeEach
    public void setup(){
        endpoint = new BrevdataEndpoint();
        endpoint.setProvider(brevdataProviderMock);
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevForBrevkodeAndUnknownBrevkode() throws Exception {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndRuntimeException() throws Exception {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndIOException() throws Exception {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new IOException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(thrown.getReason(), is("Failed when trying to read xsd-file for brev"));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevdataForSaktypeAndUnknownSaktype() throws Exception {
        when(brevdataProviderMock.getBrevdataForSaktype("Test", false)).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", false));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndIOException() throws Exception {
        when(brevdataProviderMock.getBrevdataForSaktype("Test", true)).thenThrow(new IOException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", true));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(thrown.getReason(), is("Failed when trying to read xsd-file for brev"));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndRuntimeException() throws Exception {
        when(brevdataProviderMock.getBrevdataForSaktype("Test", true)).thenThrow(new IOException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", true));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetAllBrevAndRuntimeException() throws Exception {
        when(brevdataProviderMock.getAllBrev(false)).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getAllBrev(false));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetAllBrevAndIoException() throws Exception {
        when(brevdataProviderMock.getAllBrev(true)).thenThrow(new IOException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getAllBrev(true));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(thrown.getReason(), is("Failed when trying to read xsd-file for brev"));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevkeyForBrevkodeAndRuntimeException() throws Exception {
        when(brevdataProviderMock.getBrevKeysForBrevkodeIBrevsystem(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevKeyForBrevkodeIBrevsystem("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevkoderForSaktypeAndRuntimeException() {
        when(brevdataProviderMock.getBrevkoderForSaktype(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevkoderForSaktype("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevkoderForSaktypeAndUnknownSaktype() {
        when(brevdataProviderMock.getBrevkoderForSaktype(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevkoderForSaktype("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetSprakForBrevkodeAndUnknownBrevkode() throws Exception {
        when(brevdataProviderMock.getSprakForBrevkode(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getSprakForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetSprakForBrevkodeAndRuntimeException() throws Exception {
        when(brevdataProviderMock.getSprakForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getSprakForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
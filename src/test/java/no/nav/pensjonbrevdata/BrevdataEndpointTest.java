package no.nav.pensjonbrevdata;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

    static private FakeUnleash unleash = new FakeUnleash();

    @Mock
    private BrevdataProvider brevdataProviderMock;

    private BrevdataEndpoint endpoint;

    @BeforeAll
    static public void setupForAll() {
        UnleashProvider.initialize(unleash);
    }

    @BeforeEach
    public void setup(){
        endpoint = new BrevdataEndpoint();
        endpoint.setProvider(brevdataProviderMock);
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevForBrevkodeAndUnknownBrevkode() throws IOException {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndRuntimeException() throws IOException {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndIOException() throws IOException {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevdataForSaktypeAndUnknownSaktype() throws IOException {
        when(brevdataProviderMock.getBrevdataForSaktype("Test")).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", false));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndIOException() throws IOException {
        when(brevdataProviderMock.getBrevdataForSaktype("Test")).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", true));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndRuntimeException() throws IOException {
        when(brevdataProviderMock.getBrevdataForSaktype("Test")).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", true));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetAllBrevAndRuntimeException() throws IOException {
        when(brevdataProviderMock.getAllBrev()).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getAllBrev(false));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetAllBrevAndIoException() throws IOException {
        when(brevdataProviderMock.getAllBrev()).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getAllBrev(true));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevkeyForBrevkodeAndRuntimeException() {
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
    public void shouldRespondWithBadRequestWhenGetSprakForBrevkodeAndUnknownBrevkode() {
        when(brevdataProviderMock.getSprakForBrevkode(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getSprakForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetSprakForBrevkodeAndRuntimeException() {
        when(brevdataProviderMock.getSprakForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getSprakForBrevkode("Test"));

        assertThat(thrown.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
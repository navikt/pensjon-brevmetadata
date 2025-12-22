package no.nav.pensjonbrevdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrevdataEndpointTest {

    @Mock
    private BrevdataProvider brevdataProviderMock;

    private BrevdataEndpoint endpoint;

    @BeforeEach
    public void setup(){
        endpoint = new BrevdataEndpoint(brevdataProviderMock);
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevForBrevkodeAndUnknownBrevkode() {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndRuntimeException() {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevForBrevkodeAndIOException() {
        when(brevdataProviderMock.getBrevForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevForBrevkode("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevdataForSaktypeAndUnknownSaktype() {
        when(brevdataProviderMock.getBrevdataForSaktype("Test")).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", false));

        assertThat(thrown.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndIOException() {
        when(brevdataProviderMock.getBrevdataForSaktype("Test")).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", true));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevdataForSaktypeAndRuntimeException() {
        when(brevdataProviderMock.getBrevdataForSaktype("Test")).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevdataForSaktype("Test", true));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetAllBrevAndRuntimeException() {
        when(brevdataProviderMock.getAllBrev()).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getAllBrev(false));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetAllBrevAndIoException() {
        when(brevdataProviderMock.getAllBrev()).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getAllBrev(true));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevkeyForBrevkodeAndRuntimeException() {
        when(brevdataProviderMock.getBrevKeysForBrevkodeIBrevsystem(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevKeyForBrevkodeIBrevsystem("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetBrevkoderForSaktypeAndRuntimeException() {
        when(brevdataProviderMock.getBrevkoderForSaktype(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevkoderForSaktype("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetBrevkoderForSaktypeAndUnknownSaktype() {
        when(brevdataProviderMock.getBrevkoderForSaktype(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getBrevkoderForSaktype("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithBadRequestWhenGetSprakForBrevkodeAndUnknownBrevkode() {
        when(brevdataProviderMock.getSprakForBrevkode(any())).thenThrow(new IllegalArgumentException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getSprakForBrevkode("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldRespondWithInternalServerErrorWhenGetSprakForBrevkodeAndRuntimeException() {
        when(brevdataProviderMock.getSprakForBrevkode(any())).thenThrow(new RuntimeException());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> endpoint.getSprakForBrevkode("Test"));

        assertThat(thrown.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
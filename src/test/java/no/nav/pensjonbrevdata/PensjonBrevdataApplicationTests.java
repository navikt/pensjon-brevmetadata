package no.nav.pensjonbrevdata;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import no.finn.unleash.FakeUnleash;
import no.finn.unleash.Unleash;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.nav.pensjonbrevdata.unleash.UnleashProvider;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PensjonBrevdataApplicationTests {
    static private FakeUnleash unleash = new FakeUnleash();

    @MockBean
    private Unleash defaultUnleash;

    @LocalServerPort
    private int port;

    @BeforeAll
    static public void setupForAll() {
        UnleashProvider.initialize(unleash);
    }

    @Test
    void contextLoads() {
    }


    @Test
    public void includeXsdShouldOnlyAffectPerRequest() throws IOException, InterruptedException {
        var expected = getBrevdataForSaktypeResponse("KRIGSP", false).body();
        getBrevdataForSaktypeResponse("KRIGSP", true).body();
        var actual = getBrevdataForSaktypeResponse("KRIGSP", false).body();
        assertEquals(expected, actual);
    }

    private HttpResponse<String> getBrevdataForSaktypeResponse(String brevkode, boolean includeXsd) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/brevdataForSaktype/" + brevkode + "?includeXsd="+includeXsd))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

}

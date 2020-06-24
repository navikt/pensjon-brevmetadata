package no.nav.pensjonbrevdata;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import no.finn.unleash.Unleash;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PensjonBrevdataApplicationTests {

    @MockBean
    private Unleash defaultUnleash;

    @LocalServerPort
    private int port;

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

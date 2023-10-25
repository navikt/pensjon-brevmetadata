package no.nav.pensjonbrevdata;

import io.getunleash.FakeUnleash;
import io.prometheus.client.exporter.common.TextFormat;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PensjonBrevdataApplicationTests {
    static private final FakeUnleash unleash = new FakeUnleash();

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
    public void callForAP_ENDR_OPPTJ_MAN() throws IOException, InterruptedException, JSONException {
        FakeUnleash unleash = new FakeUnleash();
        UnleashProvider.initialize(unleash);

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(
                        HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:"+port+"/api/brevdata/brevForBrevkode/AP_ENDR_OPPTJ_MAN"))
                                .build(),
                        HttpResponse.BodyHandlers.ofString()
                );
        JSONParser.parseJSON(response.body());
        assertNotNull(response.body());
        HttpStatus status = HttpStatus.resolve(response.statusCode());
        assertNotNull(status);
        assertTrue(status.is2xxSuccessful());
        unleash.resetAll();
        unleash = new FakeUnleash();
        UnleashProvider.initialize(unleash);

    }

    @Test
    public void includeXsdShouldOnlyAffectPerRequest() throws IOException, InterruptedException {
        var expected = getBrevdataForSaktypeResponse("KRIGSP", false).body();
        getBrevdataForSaktypeResponse("KRIGSP", true).body();
        var actual = getBrevdataForSaktypeResponse("KRIGSP", false).body();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnAsPlainTextForPrometheus() throws IOException, InterruptedException {
        HttpResponse response = HttpClient.newHttpClient()
                .send(
                        HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:"+port+"/api/internal/prometheus"))
                                .build(),
                        HttpResponse.BodyHandlers.ofString()
                );

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(StringUtils.deleteWhitespace(TextFormat.CONTENT_TYPE_004), StringUtils.deleteWhitespace(response.headers().firstValue("Content-Type").get().toLowerCase()));
    }

    private HttpResponse<String> getBrevdataForSaktypeResponse(String brevkode, boolean includeXsd) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/brevdataForSaktype/" + brevkode + "?includeXsd="+includeXsd))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

}

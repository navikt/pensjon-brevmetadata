package no.nav.pensjonbrevdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.finn.unleash.Unleash;
import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.google.gson.JsonParser.parseString;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KomponentTest {

    @MockBean
    private Unleash defaultUnleash;

    @LocalServerPort
    private int port;

    @Test
    public void testGetSprakForBrevkode() {
        brevkoder().forEach((E) brevkode -> testEndpoint("sprakForBrevkode",brevkode));
    }

    @Test
    public void testGetBrevForBrevkode() {
        brevkoder().forEach((E) brevkode -> testEndpoint("brevForBrevkode",brevkode));
    }

    private void testEndpoint(String endpoint, String brevkode) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/"+endpoint+"/"+brevkode))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals("Feil i respons til brevkode "+brevkode,200, resp.statusCode());
        assertEquals("Feil i respons til brevkode "+brevkode, parseString(loadResult(endpoint,brevkode)), parseString(resp.body()));
    }

    private String loadResult(String endpoint, String brevkode) throws IOException, URISyntaxException {
        URL url = getClass().getResource("/"+endpoint+"/" + brevkode);
        if (url == null)
            throw new IOException(endpoint+"-resultat ikke funnet for brevkode: " + brevkode);
        return Files.readString(Path.of(url.toURI()));
    }

    private static List<String> brevkoder() {
        return new ArrayList<>(new BrevdataMapper().getBrevMap().keySet());
    }

    private interface E extends Consumer<String> {
        void transformException(String s) throws Exception;

        @Override
        default void accept(String s) {
            try {
                transformException(s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class ResultBuilder {
        private static final BrevdataEndpoint be = new BrevdataEndpoint();
        private static Gson gson = null;

        public static void main(String[] args) {
            GsonBuilder gson = new GsonBuilder();
            gson.serializeNulls();
            ResultBuilder.gson = gson.create();
            brevkoder().stream().peek((E) ResultBuilder::fixgetBrevForBrevkode)
                    .forEach((E) ResultBuilder::fixgetSprakForBrevkode);
        }

        private static void fixgetSprakForBrevkode(String brevkode) throws IOException {
            Files.writeString(dir("sprakForBrevkode").resolve(brevkode), toJSON(be.getSprakForBrevkode(brevkode)));
        }

        private static void fixgetBrevForBrevkode(String brevkode) throws IOException {
            Files.writeString(dir("brevForBrevkode").resolve(brevkode), toJSON(be.getBrevForBrevkode(brevkode)));
        }

        private static Path dir(String stringPath) throws IOException {
            Path path = Path.of("src","test","resources",stringPath);
            if(!Files.exists(path))
                Files.createDirectories(path);
            return path;
        }

        private static CharSequence toJSON(Object object) {
            return object==null?"":gson.toJson(object);
        }
    }
}

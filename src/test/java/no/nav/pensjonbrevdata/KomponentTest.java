package no.nav.pensjonbrevdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.finn.unleash.Unleash;
import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
        brevkoder().forEach((TransformCheckedExceptionToUnchecked) brevkode -> testEndpoint("sprakForBrevkode",brevkode));
    }

    @Test
    public void testGetBrevForBrevkode() {
        brevkoder().forEach((TransformCheckedExceptionToUnchecked) brevkode -> testEndpoint("brevForBrevkode",brevkode));
    }

    @Test
    public void testGetBrevdataForSaktype() {
        sakstyper().forEach((TransformCheckedExceptionToUnchecked) this::testGetBrevdataForSaktype);
    }

    private void testEndpoint(String endpoint, String brevkode) throws IOException, InterruptedException, URISyntaxException, JSONException {
        HttpResponse<String> resp = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/"+endpoint+"/"+brevkode))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals("Feil i respons til brevkode "+brevkode,200, resp.statusCode());
        var expected = loadResult(endpoint,brevkode);
        var actual = resp.body();
        if(!(expected.equals("") && actual.equals("")))
            JSONAssert.assertEquals("Feil i respons til brevkode "+brevkode+"\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                    expected, actual, true);
    }

    private void testGetBrevdataForSaktype(String sakstype) throws IOException, InterruptedException, URISyntaxException, JSONException {
        testGetBrevdataForSaktype(sakstype, true);
        testGetBrevdataForSaktype(sakstype, false);
    }

    private void testGetBrevdataForSaktype(String sakstype, boolean includeXsd) throws IOException, InterruptedException, URISyntaxException, JSONException {
        HttpResponse<String> resp = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/brevdataForSaktype/"+sakstype+"?includeXsd="+includeXsd))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals("Feil i respons til sakstype "+sakstype,200, resp.statusCode());
        var expected = loadResult("brevdataForSaktype",sakstype,includeXsd);
        var actual = resp.body();
        if(!(expected.equals("") && actual.equals("")))
            JSONAssert.assertEquals("Feil i respons til sakstype "+sakstype+"\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                    expected, actual, true);
    }

    private String loadResult(String endpoint, String brevkode) throws IOException, URISyntaxException {
        return loadResult(endpoint, brevkode, null);
    }
    private String loadResult(String endpoint, String brevkode, Boolean includeXsd) throws IOException, URISyntaxException {
        URL url = getClass().getResource("/"+endpoint+"/" + (includeXsd != null ? includeXsd+"/" :"") + brevkode);
        if (url == null)
            throw new IOException(endpoint+"-resultat ikke funnet for kode: " + brevkode);
        return Files.readString(Path.of(url.toURI()));
    }

    private static List<String> brevkoder() {
        return new ArrayList<>(new BrevdataMapper().getBrevMap().keySet());
    }

    private static List<String> sakstyper() {
        return Arrays.asList("FAM_PL","GAM_YRK","OMSORG","AFP","BARNEP","UFOREP","GJENLEV","ALDER","GRBL","GENRL","KRIGSP","AFP_PRIVAT");
    }

    private interface TransformCheckedExceptionToUnchecked extends Consumer<String> {
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

    /**
     * Bygger en base-line av responser som applikasjonen gjør akkurat nå, og som benyttes av KomponentTest
     */
    private static class ResultBuilder {
        private static final BrevdataEndpoint be = new BrevdataEndpoint();
        private static Gson gson = null;

        public static void main(String[] args) {
            GsonBuilder gson = new GsonBuilder();
            gson.serializeNulls();
            gson.setPrettyPrinting();
            ResultBuilder.gson = gson.create();
            brevkoder().stream().peek((TransformCheckedExceptionToUnchecked) ResultBuilder::fixgetBrevForBrevkode)
                    .forEach((TransformCheckedExceptionToUnchecked) ResultBuilder::fixgetSprakForBrevkode);
            sakstyper().forEach((TransformCheckedExceptionToUnchecked) ResultBuilder::fixgetBrevdataForSaktype);
        }

        private static void fixgetSprakForBrevkode(String brevkode) throws IOException {
            Files.writeString(dir("sprakForBrevkode").resolve(brevkode), toJSON(be.getSprakForBrevkode(brevkode)));
        }

        private static void fixgetBrevForBrevkode(String brevkode) throws IOException {
            Files.writeString(dir("brevForBrevkode").resolve(brevkode), toJSON(be.getBrevForBrevkode(brevkode)));
        }
        private static void fixgetBrevdataForSaktype(String sakstype) throws IOException {
            fixgetBrevdataForSaktype(sakstype,true);
            fixgetBrevdataForSaktype(sakstype,false);
        }

        private static void fixgetBrevdataForSaktype(String sakstype, boolean includeXsd) throws IOException {
            Files.writeString(dir("brevdataForSaktype/"+includeXsd).resolve(sakstype), toJSON(be.getBrevdataForSaktype(sakstype,includeXsd)));
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

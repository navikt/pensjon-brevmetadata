package no.nav.pensjonbrevdata;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import no.finn.unleash.FakeUnleash;
import no.finn.unleash.Unleash;
import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KomponentTest {
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
    public void testGetSprakForBrevkode() {
        brevkoder().forEach((UncheckingConsumer) brevkode -> testEndpoint("sprakForBrevkode", brevkode, "brevkode"));
    }

    @Test
    public void testGetBrevForBrevkode() {
        brevkoder().forEach((UncheckingConsumer) brevkode -> testEndpoint("brevForBrevkode", brevkode, "brevkode"));
    }

    @Test
    public void testGetBrevdataForSaktype() {
        sakstyper().stream().peek((UncheckingConsumer) sakstype -> testEndpoint("brevdataForSaktype", sakstype, "sakstype",false))
                .forEach((UncheckingConsumer) sakstype -> testEndpoint("brevdataForSaktype", sakstype, "sakstype",true));
    }

    @Test
    public void testGetBrevkoderForSaktype() {
        sakstyper().forEach((UncheckingConsumer) sakstype -> testEndpoint("brevkoderForSaktype", sakstype, "sakstype"));
    }

    @Test
    public void testGetBrevKeyForBrevkodeIBrevsystem() {
        brevkoderIBrevSystem().forEach((UncheckingConsumer) brevkodeIBrevsystem -> testEndpoint("brevKeyForBrevkodeIBrevsystem", brevkodeIBrevsystem, "brevkodeIBrevsystem"));
    }

    @Test
    public void testGetAllBrev() throws InterruptedException, IOException, JSONException, URISyntaxException {
        testGetAllBrev(false);
        testGetAllBrev(true);
    }

    @Test
    public void alleBrevkoderErRepresentertITest() throws IOException, InterruptedException, JSONException {
        assertEquals(brevkoder().size(), new JSONArray(HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/allBrev?includeXsd=false"))
                .build(), HttpResponse.BodyHandlers.ofString()).body()).length(),
                "Ny brevkode er lagt til eller fjernet uten at KomponentTest.brevkoder() er oppdatert.");
    }

    private void testGetAllBrev(boolean includeXsd) throws IOException, InterruptedException, URISyntaxException, JSONException {
        HttpResponse<String> resp = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/allBrev?includeXsd="+includeXsd))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode(), "Feil i respons til allBrev med includeXsd "+includeXsd);
        var expected = loadResult("allBrev", ""+includeXsd, null);
        var actual = resp.body();
        if(!(expected.equals("") && actual.equals("")))
            JSONAssert.assertEquals("Feil i respons til allBrev med includeXsd "+includeXsd + "\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                    expected, actual, true);
    }

    private void testEndpoint(String endpoint, String kode, String kodeDesc) throws IOException, InterruptedException, URISyntaxException, JSONException {
        testEndpoint(endpoint, kode, kodeDesc, null);
    }

    private void testEndpoint(String endpoint, String kode, String kodeDesc, Boolean includeXsd) throws IOException, InterruptedException, URISyntaxException, JSONException {
        HttpResponse<String> resp = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/api/brevdata/"+endpoint+"/"+kode+(includeXsd!=null?"?includeXsd="+includeXsd:"")))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode(), "Feil i respons til " + kodeDesc + " "+kode);
        var expected = loadResult(endpoint,kode, includeXsd);
        var actual = resp.body();
        if(expected.equals("") || actual.equals("")){
            assertEquals(expected, actual,"Feil i " + kodeDesc + " " + kode);
        } else {
            JSONAssert.assertEquals("Feil i respons til " + kodeDesc + " " + kode + "\nOm man har endret i xsd-er kan man kjøre KomponentTest.ResultBuilder på nytt.",
                    expected, actual, true);
        }
    }

    private String loadResult(String endpoint, String brevkode, Boolean includeXsd) throws IOException, URISyntaxException {
        URL url = getClass().getResource("/"+endpoint+"/" + (includeXsd != null ? includeXsd+"/" :"") + brevkode);
        if (url == null)
            throw new IOException(endpoint+"-resultat ikke funnet for kode: " + brevkode);
        return Files.readString(Path.of(url.toURI()));
    }

    private static List<String> brevkoder() {
        return Arrays.asList("PE_GP_04_034","PE_UT_07_100","PE_GP_04_032","PE_GP_04_033","E202_E205","OO_MOTTAKER","PE_UT_23_101",
                "PE_UT_06_300","DUMMYBREV","PE_UP_07_105","PE_UP_07_100","FYLLER_67","VARSEL_REVURD","PE_GP_04_023","PE_GP_04_024",
                "PE_GP_04_022","PE_GP_04_027","UT_EO_VEDTAK_EB","PE_GP_04_028","PE_GP_04_025","PE_GP_04_026","PE_GP_04_029",
                "LEVEATT_PURRING_ALT","OMSORGP_EGENMLD","GJP_INFOBREV_AP_GJT","PE_IY_06_510","PE_IY_06_511","PE_UT_14_300",
                "PE_GP_04_030","PE_GP_04_031","NY_BER_UT_BT","PE_IY_05_502","AO_AP_ET_2011","PE_UT_06_200","LEVEATT_PURRING",
                "AO_AP_BT_18_2011","TILST_GJP","AO_AP_GRAD_AP_75","AO_AP_ET_BT_2011","OPPHOR_ET_GG","AFPEO_TILBAKEKREVING",
                "AFPEO_BREV_D","AP_INNV_MAN","AO_AP_FT_1967","AFPEO_BREV_B","AFPEO_BREV_C","AFPEO_BREV_A","PE_UP_07_010",
                "UP_FULLTT_BELOPENDR","PE_BA_01_107","PE_BA_01_108","R006","R004","AP_OPPH_FT_MAN","R005","OMSORG_EGEN_AUTO",
                "R002","R003","AP_INNV_AO_GJP_AUTO","UT_DOD_ENSLIG_AUTO","AP_ENDR_FLYTT_MAN","OMSORGP_GODSKRIVING","AFP_DOD_ENSLIG_AUTO",
                "UP_GRAD_AP_67","AP_RETTING_AUTO","REV_TIL_AFP_PGA_DOD","PE_BP_01_001","PE_BA_01_100","PE_BP_01_002","PE_BA_01_103",
                "PE_BA_01_104","PE_BA_01_105","PE_BA_01_106","P_UTBETALING_MINKER","AP_AVSL_ENDR","R001","ENDRING_UT_BT","PE_UT_07_200",
                "H001","AP_ENDR_EPS_MAN","H002","AP_AVSL_TIDLUTTAK","PE_OK_06_100","PE_OK_06_101","PE_OK_06_102","P1000",
                "DOD_INFO_RETT_AUTO","AO_AP_AFP_67_2011","AP_AVSL_AUTO","AP_AVSLAG_AUTO","AO_AP_FAP_PL_67_2016","AO_AP_FAP_PL_67_2011",
                "AP_AVSL_FT_MAN","INFOBREV_VP_ET","PE_UP_01_001","INFO_INNT_ENDR_UT","PE_AP_04_904","PE_AP_01_015","PE_AP_01_011",
                "PE_AP_04_901","PE_AP_04_902","PE_AP_04_903","PE_IY_03_164","PE_IY_03_165","PE_IY_03_166","PE_IY_03_167",
                "AP_DOD_ENSLIG_AUTO","PE_IY_03_047","PE_IY_03_168","BP_UTBET_OKER","PE_IY_03_048","PE_IY_03_169","PE_IY_03_049",
                "AO_AP_UT_67_2016","PE_IY_03_160","PE_IY_03_161","PE_IY_03_162","PE_IY_03_163","PE_AP_04_910","PE_AP_04_911",
                "PE_AP_04_912","PE_AP_04_913","PE_AP_04_914","PE_IY_03_175","P13000","PE_IY_03_176","PE_IY_03_177","PE_IY_03_178",
                "AP_OPPH_ENDR_FT_AUTO","PE_IY_03_179","AP_ENDR_INST_MAN","PE_IY_03_170","PE_IY_03_171","PE_IY_03_051","PE_IY_03_172",
                "PE_AP_01_019","PE_IY_03_173","PE_IY_03_174","TILST_OMRG_AP","AP_INNVILG_UTL","TILST_AP","ALDER_SAMM_IVERK",
                "PE_AP_04_920","BP_AVSL_MAN","PE_AP_04_921","PE_AP_04_922","BP_UTBET_MINKER","P12000","AP_INNV_AO_75_AUTO",
                "TILST_AFP","PE_IY_03_180","PE_AP_01_006","AP_ENDR_STANS_MAN","PE_AP_01_007","UP_HEL_67","PE_AP_01_001",
                "AP_INFO_AO67_AUTO","LEVEATT","OO_AVGIVER","UT_EO_VARSEL_FU","GJR_UTBET_MINKER","H061","AUTO_VTK_EPS_DOD_AP",
                "H062","PE_IY_04_051","PE_IY_04_050","DOD_INFO_RETT_MAN","AP_STANS_FLYTT_MAN","P4000","AP_ENDR_GRAD_AUTO",
                "GJP_FULL_INFO_67","VARSEL_TILBAKEBET","GP_INFO_AP_GT","OMR_BP_OPPH","PE_IY_04_062","PE_IY_04_060","PE_IY_04_061",
                "PE_UT_23_001","PE_UP_04_030","FORSORG_ET_OPPH","INFO_P1","AP_NYTT_VEDTAK","FAM_PL_INFO_67","OMREGN_UP_TIL_UT",
                "H070","FORSORG_ET_BT_OPPH","PE_UP_04_020","PE_UP_04_022","PE_UP_04_021","PE_IY_03_153","PE_UP_04_024","PE_IY_03_154",
                "PE_UP_04_023","PE_UP_04_026","PE_UT_01_001","PE_IY_03_156","PE_UP_04_025","PE_IY_03_157",
                "PE_UP_04_028","PE_IY_03_158","PE_UP_04_027","PE_IY_03_159","PE_UP_04_029","AFP_3MND_67","PE_IY_03_150",
                "PE_IY_03_151","PE_IY_03_152","BP_OPPH_MAN","PE_IY_04_125","PE_IY_04_126","PE_BA_04_512","P6000","PE_BA_04_511",
                "PE_IY_04_121","PE_IY_04_001","PE_BA_04_516","PE_BA_04_515","PE_IY_04_120","PE_UP_04_010","PE_BP_04_011",
                "PE_BA_04_510","PE_BP_04_010","AP_INFO_STID_MAN","OMSORG_HJST_AUTO","PE_BA_04_525","PE_UT_04_002","PE_UT_04_001",
                "PE_BA_04_523","PE_UT_04_004","PE_BA_04_522","PE_UT_04_003","PE_BA_04_528","P10000","PE_BA_04_527","AP_INNVILG_UTL_AUTO",
                "PE_BA_04_526","PE_IY_04_010","PE_BP_04_022","PE_UP_04_001","PE_BA_04_521","PE_BA_04_520","PE_BP_04_020",
                "PE_IY_04_127","PE_BP_04_021","REV_TIL_UP_PGA_DOD","AUTO_INFOBREV_GJENL","PE_IY_05_041","PE_BA_04_534","PE_UT_04_114",
                "PE_BA_04_533","AP_INNV_AO_UT_AUTO","OMSORGP_SOKNAD_MOR","PE_BA_04_532","PE_BA_04_531","PE_UT_04_115","PE_BA_04_530",
                "PE_UT_04_118","FORSORG_BT_OPPH","PE_UT_04_117","ADHOC_INFO_AUTO","OMSORGP_SOKNAD_FAR","PE_UT_04_101","P7000",
                "PE_UT_04_100","PE_UT_04_103","PE_UT_04_102","AP_AVSL_UTL_AUTO","PE_UT_04_109","PE_BP_04_001","PE_UT_04_108",
                "PE_BP_04_002","AO_AP_UP_67_2011","PE_UT_04_104","PE_UT_04_107","PE_UT_04_106","NY_BER_UT_ENDR_OPPTJ",
                "PE_FT_01_001","PE_FT_01_002","PE_FT_01_003","PE_FT_01_004","PE_IY_05_006","AP_ENDR_OPPTJ_MAN","PE_FT_01_005",
                "PE_IY_05_007","PE_FT_01_006","PE_IY_05_008","P11000","SAMMENSTOT_AP","VEDTAK_SKJERMT","PE_AF_04_010","GJP_FULL_3MND_67",
                "ALDER_IVERK","PE_GP_01_010","PE_GP_01_011","PE_IY_02_301","PE_GP_01_012","PE_AF_04_001","ENDRING_UT",
                "GJP_AVKORT_3MND_67","PE_IY_05_027","PE_IY_05_028","AP_AVSL_UTTAK","TILST_UP","AP_INNV_AVT_MAN","PE_BA_04_509",
                "PE_BA_04_508","PE_AF_03_101","PE_BA_04_503","OMSORG_EGEN_MAN","PE_BA_04_502","PE_BA_04_501","PE_AF_03_100",
                "PE_BA_04_507","PE_BA_04_506","PE_BA_04_505","PE_BA_04_504","OMSORGP_ORIENT_GODS","AP_INNV_AO_FAP_AUTO",
                "KONV_YT_ALDERSOVERG","PE_FT_01_007","PE_AF_04_020","AFP_ENDR_OPPTJ_AUTO","UP_GRAD_IKKE_AP_67","PE_AP_04_225",
                "PE_AP_04_226","AFP_PR_NYTT_VEDTAK","PE_AP_04_227","AP_ENDR_GJRETT_MAN","AP_ENDR_GRAD_MAN","GJP_AVKORT_INFO_67","VSS_INFO_EPS67_AUTO",
                "LEVEATT_ALT","AFPP_INNVILG_AUTO","OMSORGP_BARN","AP_ENDR_EPS_AUTO","ENDR_UT_PGA_INNT","P9000","PE_UT_04_400",
                "AP_INNVILG_AUTO","AFPP_AVSL_AUTO","PE_AP_04_001","PE_UT_04_402","PE_UT_04_401","PE_IY_05_200","PE_IY_05_201",
                "AO_AP_ET_60_2011","AUTO_INFOBREV_BP","PE_IY_05_104","PE_IY_05_105","OPPHOR_ET_NN","VARSEL_TILB_KREV","OMSORGP_INNVILG",
                "E210","E211","INFO_ENDR_UT_INNT","PE_AP_04_010","PE_AF_04_113","PE_AF_04_114","AP_AVSL_GJRETT_MAN","PE_AF_04_115",
                "PE_AF_04_116","PE_IY_05_111","PE_AF_04_110","HENT_INFO_MAN","REV_TIL_AP_PGA_DOD","PE_AF_04_111","PE_AF_04_112",
                "FAM_PL_3MND_67","E202","PE_AF_04_106","E204","PE_AF_04_107","PE_AP_04_020","P_UTBETALING_OKER",
                "E205","PE_AF_04_108","PE_AF_04_109","PE_AF_04_102","PE_AF_04_103","PE_AF_04_104","PE_AF_04_105","GJR_UTBET_OKER",
                "PE_IY_05_101","PE_AF_04_100","PE_IY_05_102","PE_AF_04_101","AP_ENDR_OPPTJ_AUTO","FYLLER_67_INGEN_YT","PE_IY_05_401",
                "PE_IY_05_402","AO_AP_GJP_FULL_67","PE_UT_06_100","OPPHOR_ENDRING_UT_BT","PE_IY_05_410","PE_IY_05_411","KONV_UP_TIL_UT",
                "PE_AP_05_001","AUTO_AP_ENDR_RETTING","AP_ENDR_FT_MAN","PE_AP_04_202","ADHOC_INFOBREV_AUTO",
                "PE_GP_04_010","AP_INNV_AUTO","PE_IY_05_302","PE_IY_05_303","PE_IY_05_304","PE_IY_05_305","PE_AP_04_203",
                "P8000","PE_IY_06_103","PE_AP_04_211","PE_BP_04_030","PE_AP_04_212","PE_BP_04_031",
                "TILST_DOD_UT","AP_FULLTT_BELOPENDR","PE_GP_04_020","PE_GP_04_001","PE_AP_04_214","PE_AP_04_215","PE_AP_04_216",
                "PE_UT_04_300","E001","PE_AP_04_220","PE_AP_04_221","AUTO_VTK_EPS_DOD_AFP","PE_AP_04_223",
                "PE_AP_04_224","PE_IY_05_300","PE_IY_05_301","PE_UT_05_100","VEDTAK_TILB_KREV",
                "P1100", "P5000", "P14000", "P15000", "H020", "H021", "H065", "H066", "H120", "H121",
                "X001", "X002", "X003", "X004", "X005", "X006", "X007", "X008", "X009", "X010", "X011", "X012", "X013", "X050", "X100",
                "P2000", "P2100", "P2200", "P3000", "AP_OMR_GT_AUTO","VEDTAK_TILBAKEKREV_MIDL"
                );
    }

    private static List<String> sakstyper() {
        return Arrays.asList("FAM_PL","GAM_YRK","OMSORG","AFP","BARNEP","UFOREP","GJENLEV","ALDER","GRBL","GENRL","KRIGSP","AFP_PRIVAT");
    }

    private static List<String> brevkoderIBrevSystem() {
        return new BrevdataMapper().getAllBrevAsList().stream().map(Brevdata::getBrevkodeIBrevsystem).collect(Collectors.toList());
    }

    private interface UncheckingConsumer extends Consumer<String> {
        void transformException(String s) throws Exception;

        @Override
        default void accept(String s) {
            try {
                transformException(s);
            } catch (Exception e) {
                throw new RuntimeException("Feil når den prossesserte " + s, e);
            }
        }
    }

    /**
     * Bygger en base-line av responser som applikasjonen gjør akkurat nå, og som benyttes av KomponentTest
     */
    private static class ResultBuilder {
        static {
            UnleashProvider.initialize(new FakeUnleash());
        }
        private static final BrevdataEndpoint be = new BrevdataEndpoint();
        private static final ObjectWriter objectMapper = new ObjectMapper()
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .writer(new DefaultPrettyPrinter()
                        .withObjectIndenter(new DefaultIndenter().withLinefeed("\n")));

        public static void main(String[] args) throws IOException {
            brevkoder().stream().peek((UncheckingConsumer) ResultBuilder::fixgetBrevForBrevkode)
                    .forEach((UncheckingConsumer) ResultBuilder::fixgetSprakForBrevkode);
            sakstyper().stream().peek((UncheckingConsumer) ResultBuilder::fixgetBrevdataForSaktype)
                    .forEach((UncheckingConsumer) ResultBuilder::fixgetBrevkoderForSaktype);
            brevkoderIBrevSystem().forEach((UncheckingConsumer) ResultBuilder::fixgetBrevKeyForBrevkodeIBrevsystem);
            fixGetAllBrev();
        }

        private static void fixgetBrevKeyForBrevkodeIBrevsystem(String brevkodeIBrevsystem) throws IOException {
            Files.writeString(dir("brevKeyForBrevkodeIBrevsystem").resolve(brevkodeIBrevsystem), toJSON(be.getBrevKeyForBrevkodeIBrevsystem(brevkodeIBrevsystem)));
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

        private static void fixgetBrevkoderForSaktype(String sakstype) throws IOException {
            Files.writeString(dir("brevkoderForSaktype").resolve(sakstype), toJSON(be.getBrevkoderForSaktype(sakstype)));
        }

        private static void fixGetAllBrev() throws IOException {
            fixGetAllBrev(false);
            fixGetAllBrev(true);
        }

        private static void fixGetAllBrev(boolean includeXsd) throws IOException {
            Files.writeString(dir("allBrev").resolve(""+includeXsd), toJSON(be.getAllBrev(includeXsd)));
        }

        private static Path dir(String stringPath) throws IOException {
            Path path = Path.of("src","test","resources",stringPath);
            if(!Files.exists(path))
                Files.createDirectories(path);
            return path;
        }

        private static CharSequence toJSON(Object object) throws IOException {
            if(object == null) return "";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            objectMapper.writeValue(baos, object);
            return new String(baos.toByteArray());
        }
    }
}

package no.nav.pensjonbrevdata.mappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import no.finn.unleash.FakeUnleash;

import no.nav.pensjonbrevdata.config.BrevdataFeature;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;

@ExtendWith(MockitoExtension.class)
public class BrevdataMapperTest {
    private BrevdataMapper mapper;

    private static FakeUnleash fakeUnleash = new FakeUnleash();

    @BeforeAll
    static public void setupForAll() {
        UnleashProvider.initialize(fakeUnleash);
    }

    @BeforeEach
    public void setup() {
        mapper = new BrevdataMapper();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownBrevkode() {
        String invalidBrevkode = "UGYLDIG_BREVKODE";

        assertThrows(IllegalArgumentException.class, () -> mapper.map(invalidBrevkode), "Brevkode \"" + invalidBrevkode + "\" does not exist");
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyAP_AVSLAG_AUTOAndToggleOn() throws Exception {
        String testBrev = "AP_AVSLAG_AUTO";

        fakeUnleash.enable(BrevdataFeature.BRUK_AP_AVSL_AUTO);
        mapper = new BrevdataMapper();

        Brevdata brev = mapper.map(testBrev);

        assertNotNull(brev);
        assertEquals("AP_AVSL_AUTO", brev.getBrevkodeIBrevsystem());
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyAP_AVSL_AUTOAndToggleOn() throws Exception {
        String testBrev = "AP_AVSL_AUTO";

        fakeUnleash.enable(BrevdataFeature.BRUK_AP_AVSL_AUTO);
        mapper = new BrevdataMapper();

        Brevdata brev = mapper.map(testBrev);

        assertNotNull(brev);
        assertEquals("AP_AVSL_AUTO", brev.getBrevkodeIBrevsystem());
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyPE_AP_04_210AndToggleOn() {
        String testBrev = "PE_AP_04_210";

        fakeUnleash.enable(BrevdataFeature.BRUK_AP_AVSL_AUTO);
        mapper = new BrevdataMapper();

        assertThrows(IllegalArgumentException.class, () -> mapper.map(testBrev));
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyAP_AVSL_AUTOAndToggleOff() throws Exception {
        String testBrev = "AP_AVSLAG_AUTO";

        fakeUnleash.disable(BrevdataFeature.BRUK_AP_AVSL_AUTO);
        mapper = new BrevdataMapper();

        Brevdata brev = mapper.map(testBrev);

        assertNotNull(brev);
        assertEquals("PE_AP_04_210", brev.getBrevkodeIBrevsystem());
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyPE_AP_04_210AndToggleOff() throws Exception {
        String testBrev = "PE_AP_04_210";

        fakeUnleash.disable(BrevdataFeature.BRUK_AP_AVSL_AUTO);
        mapper = new BrevdataMapper();

        Brevdata brev = mapper.map(testBrev);

        assertNotNull(brev);
        assertEquals("PE_AP_04_210", brev.getBrevkodeIBrevsystem());
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyAP_AVSLAG_AUTOAndToggleOff() {
        String testBrev = "AP_AVSL_AUTO";

        fakeUnleash.disable(BrevdataFeature.BRUK_AP_AVSL_AUTO);
        mapper = new BrevdataMapper();

        assertThrows(IllegalArgumentException.class, () -> mapper.map(testBrev));
    }
}
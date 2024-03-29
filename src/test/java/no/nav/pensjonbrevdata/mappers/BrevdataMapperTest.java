package no.nav.pensjonbrevdata.mappers;


import io.getunleash.FakeUnleash;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import no.nav.pensjonbrevdata.model.Brevdata;

@ExtendWith(MockitoExtension.class)
public class BrevdataMapperTest {
    private BrevdataMapper mapper;

    private static final FakeUnleash fakeUnleash = new FakeUnleash();

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
    public void test_TestShowAPAvslAutoWhenUsingKeyAP_AVSLAG_AUTO() {
        String testBrev = "AP_AVSLAG_AUTO";

        mapper = new BrevdataMapper();

        Brevdata brev = mapper.map(testBrev);

        assertNotNull(brev);
        assertEquals("AP_AVSL_AUTO", brev.getBrevkodeIBrevsystem());
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyAP_AVSL_AUTO() {
        String testBrev = "AP_AVSL_AUTO";

        mapper = new BrevdataMapper();

        Brevdata brev = mapper.map(testBrev);

        assertNotNull(brev);
        assertEquals("AP_AVSL_AUTO", brev.getBrevkodeIBrevsystem());
    }

    @Test
    public void test_TestShowAPAvslAutoWhenUsingKeyPE_AP_04_210() {
        String testBrev = "PE_AP_04_210";

        mapper = new BrevdataMapper();

        assertThrows(IllegalArgumentException.class, () -> mapper.map(testBrev));
    }
}
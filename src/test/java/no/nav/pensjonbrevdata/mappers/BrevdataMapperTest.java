package no.nav.pensjonbrevdata.mappers;

import no.finn.unleash.FakeUnleash;
import no.nav.pensjonbrevdata.config.BrevdataFeature;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

        assertThrows(IllegalArgumentException.class, () -> mapper.map(invalidBrevkode),"Brevkode \"" + invalidBrevkode + "\" does not exist");
    }

    @Test
    public void test_TestBreveUnleashOn() throws Exception {
        String testBrev = "TEST_BREVE";

        fakeUnleash.enable(BrevdataFeature.TEST_BREV_FEATURE);
        mapper = new BrevdataMapper();
        Object o = mapper.map(testBrev);
        assertNotNull(o);
    }

    @Test
    public void test_TestBreveUnleashOff() {
        String testBrev = "TEST_BREVE";

        fakeUnleash.disable(BrevdataFeature.TEST_BREV_FEATURE);
        mapper = new BrevdataMapper();

        assertThrows(IllegalArgumentException.class, () -> mapper.map(testBrev));
    }
}
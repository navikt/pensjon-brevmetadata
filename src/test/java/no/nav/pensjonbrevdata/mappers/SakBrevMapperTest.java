package no.nav.pensjonbrevdata.mappers;

import io.getunleash.FakeUnleash;
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMap;
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SakBrevMapperTest {
    private SakBrevMapper mapper;
    private static final FakeUnleash fakeUnleash = new FakeUnleash();

    @BeforeAll
    public static void setFakeUnleash() {
        UnleashProvider.initialize(fakeUnleash);
    }

    @BeforeEach
    public void setup() {
        mapper = new SakBrevMapper();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownSaktype() {
        String invalidBrevkode = "UGYLDIG_SAKTYPE";

        assertThrows(IllegalArgumentException.class, () -> mapper.map(invalidBrevkode), "Saktype \"" + invalidBrevkode + "\" does not exist");
    }

    @Test
    public void kunInfoP1ErInkludertNaarFjernRedigerbareDoksysbrevToggleErAktiv() {
        fakeUnleash.enable("pensjonsbrev.fjernRedigerbareDoksysbrev");
        var brevdataMapper = new BrevdataMapper();

        var alleRedigerBareDoksysBrev = brevdataMapper.getAllBrevAsList().stream()
                .filter(brev -> brev.getBrevsystem() == BrevsystemCode.DOKSYS && brev.isRedigerbart())
                .map(Brevdata::getBrevkodeIBrevsystem)
                .toList();

        for (String sakType: new SakBrevMap().keySet()) {
            var sakBrev = mapper.map(sakType);

            // ingen redigerbare doksysbrev blir returnert for sak, bortsett fra "INFO_P1"
            var redigerbareDoksysbrevForSak = sakBrev.stream()
                    .filter(alleRedigerBareDoksysBrev::contains)
                    .filter(brev -> !brev.equals("INFO_P1"))
                    .collect(toList());

            assertThat(redigerbareDoksysbrevForSak, is(empty()));
        }
    }
}
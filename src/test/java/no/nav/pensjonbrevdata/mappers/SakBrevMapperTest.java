package no.nav.pensjonbrevdata.mappers;

import io.getunleash.FakeUnleash;
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

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
    public void brevkodeSomSkalLeggesTil_SkalIkkeVaere_SynligNaarToggleErDeaktivert() {
        Set<String> brevkoderSomSkalLeggesTil = SakBrevMapper.addedBrevkoder.keySet();
        fakeUnleash.disable(SakBrevMapper.addedBrevkoder.values().stream().map(t -> t.toggle()).toArray(String[]::new));

        for (String sakType: mapper.getSakTyper()) {
            assertThat(mapper.map(sakType), everyItem(not(is(in(brevkoderSomSkalLeggesTil)))));
        }
    }

    @Test
    public void brevkodeSomSkalLeggesTil_SkalVaere_SynligNaarToggleErAktivert() {
        Set<String> brevkoderSomSkalLeggesTil = SakBrevMapper.addedBrevkoder.keySet();
        fakeUnleash.enable(SakBrevMapper.addedBrevkoder.values().stream().map(t -> t.toggle()).toArray(String[]::new));

        for (String brevKode: brevkoderSomSkalLeggesTil) {
            for (String sakType: mapper.getSakTyper()) {
                if (mapper.mapIgnoreFeatureToggle(sakType).contains(brevKode)) {
                    assertThat(mapper.map(sakType), IsIterableContaining.hasItem(brevKode));
                }
            }
        }
    }
}
package no.nav.pensjonbrevdata.mappers.sakBrev;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static no.nav.pensjonbrevdata.config.BrevdataFeature.*;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

import java.util.*;

public class SakBrevMapper {
    private final SakBrevMap sakToBrevMap = new SakBrevMap();

    // Brevkoder som skal legges til, men kun når feature er aktivert. På denne måten kan en kode legges til i dev og testes,
    // og deretter legges til i produksjon. Man kan fjerne et innslag her når det er testet og fungerer i produksjon.
    // Nøkkel skal være brevkode og verdi skal være feature-toggle.
    public static final Map<String, UnleashProvider.Toggle> addedBrevkoder = ImmutableMap.of(
            "AFP_INNV_MAN", toggle(BRUK_AFP_INNV_MAN)
            );

    // Brevkoder som skal fjernes, men kun når feature er aktivert. På denne måten kan en kode fjernes i dev og testes,
    // og deretter fjernes i produksjon. Man kan fjerne et innslag her når det er testet og fungerer i produksjon.
    public static final List<BrevkodeToRemove> removedBrevkoder = of();

    public List<String> map(String saktype) {
        return applyTogglesForRemovedBrevkoder(
                saktype,
                applyTogglesForAddedBrevkoder(mapIgnoreFeatureToggle(saktype))
        );
    }

    public List<String> mapIgnoreFeatureToggle(String saktype) {
        if (sakToBrevMap.containsKey(saktype)) {
            return sakToBrevMap.get(saktype);
        } else {
            throw new IllegalArgumentException("Saktype \"" + saktype + "\" does not exist");
        }
    }

    public Set<String> getSakTyper() {
        return new HashSet<>(sakToBrevMap.keySet());
    }

    /**
     * Filtrerer bort "nye" brevkoder basert på feature toggles, altså brevkoder som skal legges til, men ikke nødvendigvis skal være synlige i alle miljø enda.
     *
     * @param brevkoder Liste som brevkoder i deaktiverte features skal fjernes fra.
     * @return Liste uten brevkoder i deaktiverte features.
     */
    private List<String> applyTogglesForAddedBrevkoder(List<String> brevkoder) {
        return brevkoder.stream().filter(brevKode -> Optional.ofNullable(addedBrevkoder.get(brevKode)).map(UnleashProvider.Toggle::isEnabled).orElse(true)).collect(toList());
    }

    /**
     * Legger til sanerte brevkoder igjen basert på feature toggles, altså brevkoder som skal fjernes, men ikke nødvendigvis i alle miljø enda.
     *
     * @param saktype Saktypen brevkoden må være med i.
     * @param brevkoder Liste ov brevkoder (hvor sanerte brevkoder allerede er fjernet)
     * @return Liste som inkluderer alle sanerte brevkoder hvor feature toggle er deaktivert.
     */
    private List<String> applyTogglesForRemovedBrevkoder(String saktype, List<String> brevkoder) {
        Set<String> reAddBrevkoder = removedBrevkoder.stream().filter(brevkode -> brevkode.saktyper.contains(saktype))
                .filter(brevkode -> brevkode.toggle.isDisabled())
                .map(brevkode -> brevkode.brevkode)
                .collect(toSet());

        return new ArrayList<>(Sets.union(reAddBrevkoder, new HashSet<>(brevkoder)));
    }

    public record BrevkodeToRemove(String brevkode, UnleashProvider.Toggle toggle, List<String> saktyper) {
    }
}

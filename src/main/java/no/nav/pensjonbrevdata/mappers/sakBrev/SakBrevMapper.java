package no.nav.pensjonbrevdata.mappers.sakBrev;

import com.google.common.collect.ImmutableMap;
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static no.nav.pensjonbrevdata.config.BrevdataFeature.BRUK_AFP_INNV_MAN;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

@Service
public class SakBrevMapper {
    private final SakBrevMap sakToBrevMap = new SakBrevMap();
    private final BrevdataMapper brevdataMapper = new BrevdataMapper();

    // Brevkoder som skal legges til, men kun når feature er aktivert. På denne måten kan en kode legges til i dev og testes,
    // og deretter legges til i produksjon. Man kan fjerne et innslag her når det er testet og fungerer i produksjon.
    // Nøkkel skal være brevkode og verdi skal være feature-toggle.
    public static final Map<String, UnleashProvider.Toggle> addedBrevkoder = ImmutableMap.of(
            "AFP_INNV_MAN", toggle(BRUK_AFP_INNV_MAN)
    );

    public List<String> map(String saktype) {
        return filtrerUtRedigerbareDoksysbrev(
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

    private List<String> filtrerUtRedigerbareDoksysbrev(List<String> brevkoder) {
        Set<String> koder = new HashSet<>(brevkoder);
        if (UnleashProvider.toggle("pensjonsbrev.fjernRedigerbareDoksysbrev").isEnabled()) {
            koder.removeAll(brevdataMapper.getAllBrevAsList()
                    .stream()
                    .filter(kode -> kode.getBrevsystem() == BrevsystemCode.DOKSYS)
                    .filter(Brevdata::isRedigerbart)
                    .map(Brevdata::getBrevkodeIBrevsystem)
                    .collect(Collectors.toSet()));
        }
        return new ArrayList<>(koder);
    }
}

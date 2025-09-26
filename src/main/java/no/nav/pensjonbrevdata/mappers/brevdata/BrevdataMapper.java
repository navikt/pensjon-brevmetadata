package no.nav.pensjonbrevdata.mappers.brevdata;

import no.nav.pensjonbrevdata.model.Brevdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static no.nav.pensjonbrevdata.config.BrevdataFeature.BRUK_AFP_INNV_MAN;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

public class BrevdataMapper {

    private final BrevdataMap brevMap = new BrevdataMap();

    private static Function<Map<String, Brevdata>, Map<String, Brevdata>> brevdataFiltrerBortNyttBrev(String togglekey, String toggleBrevkode) {
        return brevMap -> toggle(togglekey).isEnabled() ? brevMap : brevMap.entrySet().stream().filter(entry -> !entry.getKey().equals(toggleBrevkode)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static final Function<Map<String, Brevdata>, Map<String, Brevdata>> filtrerBrevMap =
            brevdataFiltrerBortNyttBrev(BRUK_AFP_INNV_MAN, "AFP_INNV_MAN");

    public Brevdata map(String brevkode) {
        Map<String, Brevdata> filtrertBrevMap = filtrerBrevMap.apply(brevMap.get());
        if (filtrertBrevMap.containsKey(brevkode)) {
            return filtrertBrevMap.get(brevkode);
        } else {
            throw new IllegalArgumentException("Brevkode \"" + brevkode + "\" does not exist");
        }
    }

    public List<Brevdata> getAllBrevAsList() {
        List<Brevdata> brevdataList = new ArrayList<>();
        Map<String, Brevdata> filtrertBrevMap = filtrerBrevMap.apply(brevMap.get());

        for (Brevdata brevdataCallable : filtrertBrevMap.values()) {
            try {
                brevdataList.add(brevdataCallable);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return brevdataList;
    }

    public List<String> getBrevKeysForBrevkodeIBrevsystem(String brevkodeIBrevsystem) {
        List<String> brevkeys = new ArrayList<>();
        Map<String, Brevdata> filtrertBrevMap = filtrerBrevMap.apply(brevMap.get());

        for (String key : filtrertBrevMap.keySet()) {
            try {
                if (filtrertBrevMap.get(key).getBrevkodeIBrevsystem().equals(brevkodeIBrevsystem)) {
                    brevkeys.add(key);
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        return brevkeys;
    }
}

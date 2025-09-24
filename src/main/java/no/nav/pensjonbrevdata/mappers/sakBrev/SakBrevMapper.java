package no.nav.pensjonbrevdata.mappers.sakBrev;

import java.util.List;

public class SakBrevMapper {
    private final SakBrevMap sakToBrevMap = new SakBrevMap();

    public List<String> map(String saktype) {
        return mapIgnoreFeatureToggle(saktype);
    }

    public List<String> mapIgnoreFeatureToggle(String saktype) {
        if (sakToBrevMap.containsKey(saktype)) {
            return sakToBrevMap.get(saktype);
        } else {
            throw new IllegalArgumentException("Saktype \"" + saktype + "\" does not exist");
        }
    }
}

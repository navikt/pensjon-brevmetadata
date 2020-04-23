package no.nav.pensjonbrevdata.mappers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SakBrevMapper {
    private final Map<String, List<String>> sakToBrevMap;

    public SakBrevMapper() {
        sakToBrevMap = new HashMap<>();

        sakToBrevMap.put("ALDER", Arrays.asList("AP_COOL_BREV_AUTO", "AP_UNCOOL_BREV_MAN"));
        sakToBrevMap.put("AFP", Arrays.asList("AP_SERIUOSLY_COLL_BREV_MAN", "AP_UNCOOL_BREV_MAN", "AP_COOL_BREV_AUTO"));
    }

    public List<String> map(String saktype){
        return sakToBrevMap.get(saktype);
    }
}

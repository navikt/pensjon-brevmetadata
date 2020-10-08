package no.nav.pensjonbrevdata.helpers;

import no.nav.pensjonbrevdata.mappers.BrevdataMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class BrevMetaData {

    public static Set<String> getBrevTypeCodes() {
        BrevdataMapper mapper = new BrevdataMapper();
        return mapper.getAllBrevAsList().stream()
                .map(m -> m.getBrevkodeIBrevsystem())
                .collect(Collectors.toSet());
    }
}

package no.nav.pensjonbrevdata.helpers;

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.model.Brevdata;

import java.util.Set;
import java.util.stream.Collectors;

public class BrevMetaData {

    public static Set<String> getBrevTypeCodes() {
        BrevdataMapper mapper = new BrevdataMapper();
        return mapper.getAllBrevAsList().stream()
                .map(Brevdata::getBrevkodeIBrevsystem)
                .collect(Collectors.toSet());
    }
}

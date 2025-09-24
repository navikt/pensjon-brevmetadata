package no.nav.pensjonbrevdata.mappers.brevdata;

import no.nav.pensjonbrevdata.model.Brevdata;

import java.util.ArrayList;
import java.util.List;

public class BrevdataMapper {

    private final BrevdataMap brevMap = new BrevdataMap();

    public Brevdata map(String brevkode) {
        if (brevMap.containsKey(brevkode)) {
            return brevMap.get(brevkode);
        } else {
            throw new IllegalArgumentException("Brevkode \"" + brevkode + "\" does not exist");
        }
    }

    public List<Brevdata> getAllBrevAsList() {
        List<Brevdata> brevdataList = new ArrayList<>();

        for (Brevdata brevdataCallable : brevMap.values()) {
            try {
                brevdataList.add(brevdataCallable);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return brevdataList;
    }

    public List<String> getBrevKeysForBrevkodeIBrevsystem(String brevkodeIBrevsystem) {
        List<String> brevkeys = new ArrayList<>();
        for (String key : brevMap.keySet()) {
            try {
                if (brevMap.get(key).getBrevkodeIBrevsystem().equals(brevkodeIBrevsystem)) {
                    brevkeys.add(key);
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        return brevkeys;
    }
}

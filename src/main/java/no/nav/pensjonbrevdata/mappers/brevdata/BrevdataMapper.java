package no.nav.pensjonbrevdata.mappers.brevdata;

import no.nav.pensjonbrevdata.model.Brevdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BrevdataMapper {

    private final BrevdataMap brevMap = new BrevdataMap();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Function<Map<String, Brevdata>, Map<String, Brevdata>> filtrerBrevMap =
            brevMap -> brevMap.entrySet().stream().filter(entry -> !entry.getKey().equals("AFP_INNV_MAN")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

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
            } catch (IllegalArgumentException e) {
                logger.info("Illegal argument i getAllBrevAsList", e);
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
            } catch (IllegalArgumentException e) {
                logger.info("Illegal argument i getBrevKeysForBrevkodeIBrevsystem", e);
            }
        }
        return brevkeys;
    }
}

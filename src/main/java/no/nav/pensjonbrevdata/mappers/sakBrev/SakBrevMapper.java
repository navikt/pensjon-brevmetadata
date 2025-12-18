package no.nav.pensjonbrevdata.mappers.sakBrev;

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SakBrevMapper {
    private final SakBrevMap sakToBrevMap = new SakBrevMap();
    private final BrevdataMapper brevdataMapper = new BrevdataMapper();

    public List<String> map(String saktype) {
        Set<String> koder = mapIgnoreFeatureToggle(saktype).stream().filter(brevKode -> !brevKode.equals("AFP_INNV_MAN")).collect(Collectors.toSet());

        if (UnleashProvider.toggle("pensjonsbrev.fjernRedigerbareDoksysbrev").isEnabled()) {
            koder.removeAll(brevdataMapper.getAllBrevAsList()
                    .stream()
                    .filter(kode -> kode.getBrevsystem() == BrevsystemCode.DOKSYS)
                    .filter(kode -> !kode.getBrevkodeIBrevsystem().equals("INFO_P1"))
                    .filter(Brevdata::isRedigerbart)
                    .map(Brevdata::getBrevkodeIBrevsystem)
                    .collect(Collectors.toSet()));
        }
        return new ArrayList<>(koder);
    }

    public List<String> mapIgnoreFeatureToggle(String saktype) {
        if (sakToBrevMap.containsKey(saktype)) {
            return sakToBrevMap.get(saktype);
        } else {
            throw new IllegalArgumentException("Saktype \"" + saktype + "\" does not exist");
        }
    }
}

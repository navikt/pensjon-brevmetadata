package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevMapper;
import no.nav.pensjonbrevdata.model.Brev;

import java.util.List;

public class BrevdataProvider {
    private BrevMapper brevMapper = new BrevMapper();

    public List<String> getSprakForBrevkode(String brevkode) throws Exception {
        return brevMapper.map(brevkode).getSprak();
    }

    public Brev getBrevForBrevkode(String brevkode) throws Exception {
        return brevMapper.map(brevkode);
    }
}

package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevMapper;
import no.nav.pensjonbrevdata.model.Brev;

import java.util.List;

public class BrevdataProvider {
    private BrevMapper brevMapper = new BrevMapper();

    public List<String> getSprakForBrev(String brevkode){
        return brevMapper.map(brevkode).getSprak();
    }

    public Brev getBrevForBrevkode(String brevkode){
        return brevMapper.map(brevkode);
    }
}

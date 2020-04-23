package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brev;
import no.nav.pensjonbrevdata.model.SprakCode;

import java.util.ArrayList;
import java.util.List;

public class BrevdataProvider {
    private BrevMapper brevMapper = new BrevMapper();
    private SakBrevMapper sakBrevMapper = new SakBrevMapper();

    public List<SprakCode> getSprakForBrevkode(String brevkode) throws Exception {
        return brevMapper.map(brevkode).getSprak();
    }

    public Brev getBrevForBrevkode(String brevkode) throws Exception {
        Brev brev = brevMapper.map(brevkode);

        return brev;
    }

    public List<Brev> getBrevdataForSaktype(String saktype) throws Exception {
        List<Brev> brevList = new ArrayList<>();
        for (String brevkode : sakBrevMapper.map(saktype)) {
            brevList.add(brevMapper.map(brevkode));
        }
        return brevList;
    }

    public List<String> getBrevkoderForSaktype(String saktype){
        return sakBrevMapper.map(saktype);
    }
}

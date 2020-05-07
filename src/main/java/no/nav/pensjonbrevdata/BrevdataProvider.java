package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brev;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.SprakCode;

import java.util.ArrayList;
import java.util.List;

public class BrevdataProvider {
    private final BrevdataMapper brevdataMapper = new BrevdataMapper();
    private final SakBrevMapper sakBrevMapper = new SakBrevMapper();

    public List<SprakCode> getSprakForBrevkode(String brevkode) throws Exception {
        Brevdata brevdata = brevdataMapper.map(brevkode);
        return brevdata instanceof Brev ? ((Brev) brevdata).getSprak() : null;
    }

    public Brevdata getBrevForBrevkode(String brevkode) throws Exception {
        return brevdataMapper.map(brevkode);
    }

    public List<Brevdata> getBrevdataForSaktype(String saktype) throws Exception {
        List<Brevdata> brevList = new ArrayList<>();
        for (String brevkode : sakBrevMapper.map(saktype)) {
            brevList.add(getBrevForBrevkode(brevkode));
        }
        return brevList;
    }

    public List<String> getBrevkoderForSaktype(String saktype){
        return sakBrevMapper.map(saktype);
    }
}

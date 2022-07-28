package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.SprakCode;

import java.util.ArrayList;
import java.util.List;

public class BrevdataProvider {
    private BrevdataMapper brevdataMapper;
    private SakBrevMapper sakBrevMapper;

    public BrevdataProvider() {
        brevdataMapper = new BrevdataMapper();
        sakBrevMapper = new SakBrevMapper();
    }

    public List<SprakCode> getSprakForBrevkode(String brevkode) {
        Brevdata brevdata = brevdataMapper.map(brevkode);
        return brevdata.getSprak();
    }

    public Brevdata getBrevForBrevkode(String brevkode) {
        return brevdataMapper.map(brevkode);
    }

    public List<Brevdata> getBrevdataForSaktype(String saktype){
        List<Brevdata> brevList = new ArrayList<>();
        for (String brevkode : sakBrevMapper.map(saktype)) {
            Brevdata brevdata = brevdataMapper.map(brevkode);
            brevList.add(brevdata);
        }
        return brevList;
    }

    public List<String> getBrevkoderForSaktype(String saktype) {
        return sakBrevMapper.map(saktype);
    }

    public List<Brevdata> getAllBrev() {
        return brevdataMapper.getAllBrevAsList();
    }

    public List<String> getBrevKeysForBrevkodeIBrevsystem(String brevkodeIBrevsystem) {
        return brevdataMapper.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem);
    }

    public void setBrevdataMapper(BrevdataMapper brevdataMapper) {
        this.brevdataMapper = brevdataMapper;
    }

    public void setSakBrevMapper(SakBrevMapper sakBrevMapper) {
        this.sakBrevMapper = sakBrevMapper;
    }
}

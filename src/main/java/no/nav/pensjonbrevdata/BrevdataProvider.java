package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brev;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.Doksysbrev;
import no.nav.pensjonbrevdata.model.codes.SprakCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class BrevdataProvider {
    private BrevdataMapper brevdataMapper;
    private SakBrevMapper sakBrevMapper;

    public BrevdataProvider() {
        brevdataMapper = new BrevdataMapper();
        sakBrevMapper = new SakBrevMapper();
    }

    public List<SprakCode> getSprakForBrevkode(String brevkode) throws Exception {
        Brevdata brevdata = brevdataMapper.map(brevkode);
        return brevdata instanceof Brev ? ((Brev) brevdata).getSprak() : null;
    }

    public Brevdata getBrevForBrevkode(String brevkode) throws Exception {
        Brevdata brevdata = brevdataMapper.map(brevkode);
        if (brevdata instanceof Doksysbrev) {
            ((Doksysbrev) brevdata).generateDokumentmalFromFile();
        }
        return brevdata;
    }

    public List<Brevdata> getBrevdataForSaktype(String saktype, boolean includeXsd) throws Exception {
        List<Brevdata> brevList = new ArrayList<>();
        for (String brevkode : sakBrevMapper.map(saktype)) {
            Brevdata brevdata = brevdataMapper.map(brevkode);
            if (includeXsd && brevdata instanceof Doksysbrev) {
                ((Doksysbrev) brevdata).generateDokumentmalFromFile();
            }
            brevList.add(brevdata);
        }
        return brevList;
    }

    public List<String> getBrevkoderForSaktype(String saktype) {
        return sakBrevMapper.map(saktype);
    }

    public List<Brevdata> getAllBrev(boolean includeXsd) throws Exception {
        List<Brevdata> brevdataList = brevdataMapper.getAllBrevAsList();
        if (includeXsd) {
            for (Brevdata brevdata : brevdataList) {
                if (brevdata instanceof Doksysbrev) {
                    ((Doksysbrev) brevdata).generateDokumentmalFromFile();
                }
            }
        }
        return brevdataList;
    }

    public List<String> getBrevKeysForBrevkodeIBrevsystem(String brevkodeIBrevsystem) throws Exception {
        Map<String, Callable<Brevdata>> brevdataMap = brevdataMapper.getBrevMap();
        List<String> brevkeys = new ArrayList<>();
        for (String key : brevdataMap.keySet()) {
            if (brevdataMap.get(key).call().getBrevkodeIBrevsystem().equals(brevkodeIBrevsystem)) {
                brevkeys.add(key);
            }
        }
        return brevkeys;
    }

    public void setBrevdataMapper(BrevdataMapper brevdataMapper) {
        this.brevdataMapper = brevdataMapper;
    }

    public void setSakBrevMapper(SakBrevMapper sakBrevMapper) {
        this.sakBrevMapper = sakBrevMapper;
    }
}

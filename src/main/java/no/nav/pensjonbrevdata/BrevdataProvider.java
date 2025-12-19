package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.DokumentkategoriCode;
import no.nav.pensjonbrevdata.model.codes.SprakCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrevdataProvider {
    private final BrevdataMapper brevdataMapper;
    private final SakBrevMapper sakBrevMapper;

    @Autowired
    public BrevdataProvider(BrevdataMapper brevdataMapper, SakBrevMapper sakBrevMapper) {
        this.brevdataMapper = brevdataMapper;
        this.sakBrevMapper = sakBrevMapper;
    }

    public List<SprakCode> getSprakForBrevkode(String brevkode) {
        return brevdataMapper.map(brevkode).getSprak();
    }

    public Brevdata getBrevForBrevkode(String brevkode) {
        return brevdataMapper.map(brevkode);
    }

    public List<Brevdata> getBrevdataForSaktype(String saktype){
        return sakBrevMapper.map(saktype).stream()
                .map(brevdataMapper::map)
                .collect(Collectors.toList());
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

    public List<Brevdata> getEblanketter() {
        return brevdataMapper.getAllBrevAsList().stream()
                .filter(brev -> brev.getDokumentkategori() == DokumentkategoriCode.E_BLANKETT)
                .toList();
    }
}

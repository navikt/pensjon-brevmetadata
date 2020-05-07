package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.*;
import no.nav.pensjonbrevdata.model.codes.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class BrevdataMapper {

    private final Map<String, Callable<Brevdata>> brevMap;

    public BrevdataMapper() {
        brevMap = new HashMap<>();
        DoksysVedleggMapper doksysVedleggMapper = new DoksysVedleggMapper();
        brevMap.put("AP_COOL_BREV_AUTO", () ->
                new GammeltBrev("AP_COOL_BREV_AUTO",
                        false,
                        "Et kult brev som er automatisk",
                        BrevkategoriCode.INFORMASJON,
                        DokumenttypeCode.N,
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN),
                        true,
                        BrevUtlandCode.ALLTID,
                        BrevregeltypeCode.GN,
                        BrevkravtypeCode.ALLE,
                        DokumentkategoriCode.F,
                        false,
                        BrevkontekstCode.ALLTID,
                        "brevgr008"));
        brevMap.put("AP_COOL_BREV_MAN", () ->
                new GammeltBrev("AP_COOL_BREV_MAN",
                        true,
                        "Et kult brev som er manuelt",
                        BrevkategoriCode.BREV_MED_SKJEMA,
                        DokumenttypeCode.N,
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN),
                        true,
                        BrevUtlandCode.ALLTID,
                        BrevregeltypeCode.GN,
                        BrevkravtypeCode.ALLE,
                        DokumentkategoriCode.F,
                        false,
                        BrevkontekstCode.ALLTID,
                        "brevgr002"));
        brevMap.put("AP_UNCOOL_BREV_MAN", () ->
                new GammeltBrev("AP_UNCOOL_BREV_MAN",
                        true,
                        "Et ukult brev som er manuelt",
                        BrevkategoriCode.VEDTAK,
                        DokumenttypeCode.I,
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN),
                        true,
                        BrevUtlandCode.ALLTID,
                        BrevregeltypeCode.OVRIGE,
                        BrevkravtypeCode.ALLE,
                        DokumentkategoriCode.EP,
                        false,
                        BrevkontekstCode.ALLTID,
                        "brevgr003"));
        brevMap.put("BATCHBREV_TESTBREV", () ->
                new GammeltBrev("ANNEN_KODE_ENN_MAP_KEY_HER_NAAR_BATCHBREV",
                        true,
                        "Et ukult brev som er manuelt",
                        BrevkategoriCode.VARSEL,
                        DokumenttypeCode.U,
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN),
                        true,
                        BrevUtlandCode.ALLTID,
                        BrevregeltypeCode.NN,
                        BrevkravtypeCode.ALLE,
                        DokumentkategoriCode.KD,
                        false,
                        BrevkontekstCode.ALLTID,
                        "brevgr008"));
        brevMap.put("AP_SERIUOSLY_COOL_BREV_MAN", () ->
                new Doksysbrev("AP_SERIUOSLY_COOL_BREV_MAN",
                        true,
                        "Et seriøst kult brev",
                        BrevkategoriCode.INFORMASJON,
                        DokumenttypeCode.N,
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN),
                        true,
                        BrevUtlandCode.UTLAND,
                        BrevregeltypeCode.OVRIGE,
                        BrevkravtypeCode.EKSPORT,
                        DokumentkategoriCode.KS,
                        true,
                        BrevkontekstCode.ALLTID,
                        "001222",
                        "00001",
                        doksysVedleggMapper.map("RETTIGH_SERIOUSLY_VEDLEGG_V4", "RETTIGH_UNCOOL_VEDLEGG_V2")));
        brevMap.put("TESTBREV", () ->
                new Doksysbrev("TESTBREV",
                        true,
                        "Et brev for å test nye muligheter",
                        BrevkategoriCode.VEDTAK,
                        DokumenttypeCode.I,
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN),
                        true,
                        BrevUtlandCode.ALLTID,
                        BrevregeltypeCode.OVRIGE,
                        BrevkravtypeCode.ENDR_UTTAKSGRAD,
                        DokumentkategoriCode.KS,
                        true,
                        BrevkontekstCode.ALLTID,
                        "001221",
                        "00001",
                        doksysVedleggMapper.map("RETTIGH_SERIOUSLY_VEDLEGG_V4", "RETTIGH_COOL_VEDLEGG_V1")));
    }

    public Brevdata map(String brevkode) throws Exception {
        return brevMap.get(brevkode).call();
    }
}

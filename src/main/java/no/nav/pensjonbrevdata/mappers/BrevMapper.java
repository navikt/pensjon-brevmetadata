package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.Brev;
import no.nav.pensjonbrevdata.model.Doksysbrev;
import no.nav.pensjonbrevdata.model.SprakCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class BrevMapper {

    private final Map<String, Callable<Brev>> brevMap;

    public BrevMapper() {
        brevMap = new HashMap<>();
        DoksysVedleggMapper doksysVedleggMapper = new DoksysVedleggMapper();
        brevMap.put("AP_COOL_BREV_AUTO", () ->
                new Brev(
                            false,
                            "Et kult brev som er automatisk",
                            "U",
                            "Ukjent",
                            Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN)));
        brevMap.put("AP_COOL_BREV_MAN", () ->
                new Brev(
                        true,
                        "Et kult brev som er manuelt",
                        "U",
                        "Ukjent",
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN)));
        brevMap.put("AP_UNCOOL_BREV_MAN", () ->
                new Brev(
                        true,
                        "Et ukult brev som er manuelt",
                        "U",
                        "Ukjent",
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN)));
        brevMap.put("AP_SERIUOSLY_COLL_BREV_MAN", () ->
                new Doksysbrev(
                        true,
                        "Et seriøst kult brev",
                        "U",
                        "Ukjent",
                        "001222",
                        "00001",
                        doksysVedleggMapper.map("RETTIGH_SERIOUSLY_VEDLEGG_V4", "RETTIGH_UNCOOL_VEDLEGG_V2"),
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN)));
        brevMap.put("TESTBREV", () ->
                new Doksysbrev(
                        true,
                        "Et brev for å test nye muligheter",
                        "U",
                        "Ukjent",
                        "001221",
                        "00001",
                        doksysVedleggMapper.map("RETTIGH_SERIOUSLY_VEDLEGG_V4", "RETTIGH_COOL_VEDLEGG_V1"),
                        Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN)));
    }

    public Brev map(String brevkode) throws Exception {
        return brevMap.get(brevkode).call();
    }
}

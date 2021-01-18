package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.DoksysVedlegg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static no.nav.pensjonbrevdata.config.BrevdataFeature.*;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

public class DoksysVedleggMapper {
    private final Map<String, DoksysVedlegg> vedleggMap;

    private static Function<Map<String, DoksysVedlegg>, Map<String, DoksysVedlegg>> brevdataErstattMedGammeltVedlegg(String togglekey, String toggleVedleggkode, DoksysVedlegg gammeltVedlegg) {
        return vedleggMap -> toggle(togglekey).isEnabled() ? vedleggMap : vedleggMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getKey().equals(toggleVedleggkode) ? gammeltVedlegg : entry.getValue()));
    }

//    private static final DoksysVedlegg GAMMEL_VEDLEGG_00004 =
//            new DoksysVedlegg(
//                    "AP_OPPL_BER_V1",
//                    "VEDLEGG: Opplysninger brukt i beregningen. Versjon 1",
//                    "V00004",
//                    "00001");
//
    private static final Function<Map<String, DoksysVedlegg>, Map<String, DoksysVedlegg>> filtrerVedleggMap = m -> m ;
//            brevdataErstattMedGammeltVedlegg(BRUK_ALDERSOVERGANGKATEGORI_I_V00004, "AP_OPPL_BER_V1", GAMMEL_VEDLEGG_00004);


    public DoksysVedleggMapper() {
        vedleggMap = new HashMap<>();
        vedleggMap.put("RETTIGH_V1",
                new DoksysVedlegg(
                        "RETTIGH_V1",
                        "VEDLEGG: Rettigheter. Versjon 1",
                        "V00001",
                        "00001"));
        vedleggMap.put("RETTIGH_PLIKT_V1",
                new DoksysVedlegg(
                        "RETTIGH_PLIKT_V1",
                        "VEDLEGG: Orientering om rettigheter og plikter. Versjon 1",
                        "V00002",
                        "00001"));
        vedleggMap.put("AP_MND_UTB_V1",
                new DoksysVedlegg(
                        "AP_MND_UTB_V1",
                        "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1	",
                        "V00003",
                        "00001"));
        vedleggMap.put("AP_OPPL_BER_V1",
                new DoksysVedlegg(
                        "AP_OPPL_BER_V1",
                        "VEDLEGG: Opplysninger brukt i beregningen. Versjon 1",
                        "V00004",
                        "00001"));
        vedleggMap.put("AP_OPPL_BER_END_V1",
                new DoksysVedlegg(
                        "AP_OPPL_BER_END_V1",
                        "VEDLEGG: Opplysninger brukt i beregningen endret uttaksgrad. Versjon 1",
                        "V00005",
                        "00001"));
        vedleggMap.put("AP_AVDOD_OPPL_BER_V1",
                new DoksysVedlegg(
                        "AP_AVDOD_OPPL_BER_V1",
                        "VEDLEGG: Opplysninger om avdøde brukt i beregningen.",
                        "V00006",
                        "00001"));
        vedleggMap.put("AP_MND_UTB_V4",
                new DoksysVedlegg(
                        "AP_MND_UTB_V4",
                        "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 4",
                        "V00007",
                        "00001"));
        vedleggMap.put("AFP_PRIV_MND_UTB_V1",
                new DoksysVedlegg(
                        "AFP_PRIV_MND_UTB_V1",
                        "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1",
                        "V00008",
                        "00001"));
        vedleggMap.put("INFO_MEDLEM_HELSE_V1",
                new DoksysVedlegg(
                        "INFO_MEDLEM_HELSE_V1",
                        "VEDLEGG: Informasjon om medlemskap i folketrygden og rett til helsetjenester",
                        "V00009",
                        "00001"));

    }

    public List<DoksysVedlegg> map(String... vedleggCodes) {
        List<DoksysVedlegg> vedleggList = new ArrayList<>();
        Map<String, DoksysVedlegg> filtrertVedleggMap = filtrerVedleggMap.apply(vedleggMap);
        for (String vedleggCode : vedleggCodes) {
            vedleggList.add(filtrertVedleggMap.get(vedleggCode));
        }
        return vedleggList;
    }
}

package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.DoksysVedlegg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class DoksysVedleggMapper {
    private final Map<String, Callable<DoksysVedlegg>> vedleggMap;

    public DoksysVedleggMapper() {
        vedleggMap = new HashMap<>();
        vedleggMap.put("RETTIGH_PLIKT_V1", () ->
                new DoksysVedlegg(
                        "RETTIGH_PLIKT_V1",
                        "VEDLEGG: Orientering om rettigheter og plikter. Versjon 1",
                        "V00002",
                        "00001"));
        vedleggMap.put("AP_MND_UTB_V1", () ->
                new DoksysVedlegg(
                        "AP_MND_UTB_V1",
                        "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1	",
                        "V00003",
                        "00001"));
        vedleggMap.put("AP_OPPL_BER_V1", () ->
                new DoksysVedlegg(
                        "AP_OPPL_BER_V1",
                        "VEDLEGG: Opplysninger brukt i beregningen. Versjon 1",
                        "V00004",
                        "00001"));
        vedleggMap.put("RETTIGH_V1", () ->
                new DoksysVedlegg(
                        "RETTIGH_V1",
                        "VEDLEGG: Rettigheter. Versjon 1",
                        "V00001",
                        "00001"));
        vedleggMap.put("AP_OPPL_BER_END_V1", () ->
                new DoksysVedlegg(
                        "AP_OPPL_BER_END_V1",
                        "VEDLEGG: Opplysninger brukt i beregningen endret uttaksgrad. Versjon 1",
                        "V00005",
                        "00001"));
        vedleggMap.put("AFP_PRIV_MND_UTB_V1", () ->
                new DoksysVedlegg(
                        "AFP_PRIV_MND_UTB_V1",
                        "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1",
                        "V00008",
                        "00001"));
        vedleggMap.put("AP_AVDOD_OPPL_BER_V1", () ->
                new DoksysVedlegg(
                        "AP_AVDOD_OPPL_BER_V1",
                        "VEDLEGG: Opplysninger om avdøde brukt i beregningen.",
                        "V00006",
                        "00001"));
        vedleggMap.put("AP_MND_UTB_V4", () ->
                new DoksysVedlegg(
                        "AP_MND_UTB_V4",
                        "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 4",
                        "V00007",
                        "00001"));

    }

    public List<DoksysVedlegg> map(String... vedleggCodes) {
        List<DoksysVedlegg> vedleggList = new ArrayList<>();
        for (String vedleggCode : vedleggCodes) {
            try {
                vedleggList.add(vedleggMap.get(vedleggCode).call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vedleggList;
    }
}

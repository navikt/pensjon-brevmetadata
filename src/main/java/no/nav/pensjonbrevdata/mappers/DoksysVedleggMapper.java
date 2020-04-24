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
        vedleggMap.put("RETTIGH_SERIOUSLY_VEDLEGG_V4", () -> new DoksysVedlegg(
                "Vedlegg om andre ting",
                "V0001",
                "00002"));
        vedleggMap.put("RETTIGH_UNCOOL_VEDLEGG_V2", () ->
                new DoksysVedlegg(
                        "Vedlegg om utbetaling",
                        "V0002",
                        "00002"));
        vedleggMap.put("RETTIGH_COOL_VEDLEGG_V1", () ->
                new DoksysVedlegg(
                        "Vedlegg om utbetaling",
                        "V0003",
                        "00002"));
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

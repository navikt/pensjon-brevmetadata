package no.nav.pensjonbrevdata.mappers.doksysVedlegg;

import no.nav.pensjonbrevdata.model.DoksysVedlegg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DoksysVedleggMapper {
    private final DoksysVedleggMap vedleggMap = new DoksysVedleggMap();

    private static final DoksysVedlegg gamleVedlegg8 = new DoksysVedlegg(
            "AFP_PRIV_MND_UTB_V1",
            "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1",
            "V00008",
            "00001");


    private static final Function<Map<String, DoksysVedlegg>, Map<String, DoksysVedlegg>> filtrerVedleggMap =
            vedleggMap -> vedleggMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getKey().equals("AFP_PRIV_MND_UTB_V1") ? gamleVedlegg8 : entry.getValue()));

    public Supplier<List<DoksysVedlegg>> map(String... vedleggCodes) {
        return () -> {
            List<DoksysVedlegg> vedleggList = new ArrayList<>();
            Map<String, DoksysVedlegg> filtrertVedleggMap = filtrerVedleggMap.apply(vedleggMap.get());
            for (String vedleggCode : vedleggCodes) {
                vedleggList.add(filtrertVedleggMap.get(vedleggCode));
            }
            return vedleggList;
        };
    }
}

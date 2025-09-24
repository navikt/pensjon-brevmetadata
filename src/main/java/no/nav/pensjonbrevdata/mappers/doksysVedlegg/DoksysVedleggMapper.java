package no.nav.pensjonbrevdata.mappers.doksysVedlegg;

import no.nav.pensjonbrevdata.model.DoksysVedlegg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static no.nav.pensjonbrevdata.config.BrevdataFeature.ERSTATT_AFP_PRIV_MND_UTB_V1;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

public class DoksysVedleggMapper {
    private final DoksysVedleggMap vedleggMap = new DoksysVedleggMap();

    private static Function<Map<String, DoksysVedlegg>, Map<String, DoksysVedlegg>> brevdataErstattMedGammeltVedlegg(String togglekey, String toggleVedleggkode, DoksysVedlegg gammeltVedlegg) {
        return vedleggMap -> toggle(togglekey).isEnabled() ? vedleggMap : vedleggMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getKey().equals(toggleVedleggkode) ? gammeltVedlegg : entry.getValue()));
    }

    private static final DoksysVedlegg gamleVedlegg8 = new DoksysVedlegg(
            "AFP_PRIV_MND_UTB_V1",
            "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1",
            "V00008",
            "00001");


    private static final Function<Map<String, DoksysVedlegg>, Map<String, DoksysVedlegg>> filtrerVedleggMap =
            brevdataErstattMedGammeltVedlegg(ERSTATT_AFP_PRIV_MND_UTB_V1, "AFP_PRIV_MND_UTB_V1", gamleVedlegg8);

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

package no.nav.pensjonbrevdata.mappers.doksysVedlegg;

import no.nav.pensjonbrevdata.model.DoksysVedlegg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DoksysVedleggMapper {
    private final DoksysVedleggMap vedleggMap = new DoksysVedleggMap();

    public Supplier<List<DoksysVedlegg>> map(String... vedleggCodes) {
        return () -> {
            List<DoksysVedlegg> vedleggList = new ArrayList<>();
            for (String vedleggCode : vedleggCodes) {
                vedleggList.add(vedleggMap.get().get(vedleggCode));
            }
            return vedleggList;
        };
    }
}

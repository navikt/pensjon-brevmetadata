package no.nav.pensjonbrevdata.mappers.doksysVedlegg;

import no.nav.pensjonbrevdata.model.DoksysVedlegg;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class DoksysVedleggMapper {
    private final DoksysVedleggMap vedleggMap = new DoksysVedleggMap();

    public Supplier<List<DoksysVedlegg>> map(String... vedleggCodes) {
        return () -> Arrays.stream(vedleggCodes).map(vedleggMap::get).toList();
    }
}

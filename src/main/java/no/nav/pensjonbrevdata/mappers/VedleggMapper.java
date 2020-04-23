package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.Vedlegg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class VedleggMapper {
    private final Map<String, Callable<Vedlegg>> vedleggMap;

    public VedleggMapper() {
        vedleggMap = new HashMap<>();
        vedleggMap.put("V0001", () -> new Vedlegg(
                "Vedlegg om andre ting",
                "000897"));
        vedleggMap.put("V0004", () ->
                new Vedlegg(
                        "Vedlegg om utbetaling",
                        "000894"));
    }

    public List<Vedlegg> map(String... vedleggCodes) {
        List<Vedlegg> vedleggList = new ArrayList<>();
        for (String vedleggCode : vedleggCodes) {//Streams kanskje
            try {
                vedleggList.add(vedleggMap.get(vedleggCode).call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vedleggList;
    }
}

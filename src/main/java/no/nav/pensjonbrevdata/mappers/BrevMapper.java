package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.Brev;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class BrevMapper {

    private final Map<String, Callable<Brev>> brevMap;

    public BrevMapper() {
        brevMap = new HashMap<>();
        VedleggMapper vedleggMapper = new VedleggMapper();
        brevMap.put("AP_COOL_BREV_AUTO", () ->
                new Brev(
                            false,
                            "Et kult brev som er automatisk",
                            "U",
                            "Ukjent",
                            "000765",
                            vedleggMapper.map("V0001", "V0004"),
                            Arrays.asList("BKM", "NYN", "ENG")));
        brevMap.put("AP_COOL_BREV_MAN", () ->
                new Brev(
                        true,
                        "Et kult brev som er manuelt",
                        "U",
                        "Ukjent",
                        "000767",
                        vedleggMapper.map("V0002", "V0004"),
                        Arrays.asList("BKM", "NYN", "ENG")));
    }

    public Brev map(String brevkode) throws Exception {
        return brevMap.get(brevkode).call();
    }
}

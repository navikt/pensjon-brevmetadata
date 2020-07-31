package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;
import java.util.function.Function;

public class GammeltBrev extends Brevdata {

    private String brevgruppe;

    public GammeltBrev(String brevkodeInBrevsystem,
                       boolean redigerbart,
                       String dekode,
                       BrevkategoriCode brevkategori,
                       DokumenttypeCode doktype,
                       List<SprakCode> sprak,
                       Boolean visIPselv,
                       BrevUtlandCode utland,
                       BrevregeltypeCode brevregeltype,
                       BrevkravtypeCode brevkravtype,
                       DokumentkategoriCode dokumentkategori,
                       Boolean synligForVeileder,
                       BrevkontekstCode brevkontekst,
                       Integer prioritet,
                       String brevgruppe) {
        super(brevkodeInBrevsystem,
                redigerbart,
                dekode,
                brevkategori,
                doktype,
                sprak,
                visIPselv,
                utland,
                brevregeltype,
                brevkravtype,
                dokumentkategori,
                synligForVeileder,
                brevkontekst,
                BrevsystemCode.GAMMEL,
                prioritet);
        this.brevgruppe = brevgruppe;
    }

    public String getBrevgruppe() {
        return brevgruppe;
    }

    @Override
    public Brevdata medXSD(Function<String, String> dokumentmalGenerator, Function<String, String> fellesmalGenerator) {
        return this;
    }
}

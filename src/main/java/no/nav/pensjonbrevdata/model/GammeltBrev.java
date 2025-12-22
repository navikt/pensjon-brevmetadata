package no.nav.pensjonbrevdata.model;

import kotlin.jvm.functions.Function1;
import no.nav.pensjonbrevdata.dto.BrevdataDTO;
import no.nav.pensjonbrevdata.model.codes.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GammeltBrev extends Brevdata implements BrevdataDTO {

    private final String brevgruppe;

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
    public Brevdata medXSD(@NotNull Function1<String, String> dokumentmalGenerator, @NotNull Function1<String, String> fellesmalGenerator) {
        return this;
    }

    @Override
    public BrevdataDTO toDTO() {
        return this;
    }
}

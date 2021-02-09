package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoksysbrevV2 extends Doksysbrev {
    public DoksysbrevV2(String brevkodeInBrevsystem, boolean redigerbart, String dekode, BrevkategoriCode brevkategori, DokumenttypeCode doktype, List<SprakCode> sprak, Boolean visIPselv, BrevUtlandCode utland, BrevregeltypeCode brevregeltype, BrevkravtypeCode brevkravtype, DokumentkategoriCode dokumentkategori, Boolean synligForVeileder, BrevkontekstCode brevkontekst, Integer prioritet, String dokumentmalId, String dokumentmalFelleselementId, Supplier<List<DoksysVedlegg>> vedleggListe) {
        super(brevkodeInBrevsystem, redigerbart, dekode, brevkategori, doktype, sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori, synligForVeileder, brevkontekst, prioritet, dokumentmalId, dokumentmalFelleselementId, vedleggListe);
    }

    @Override
    public Brevdata medXSD(Function<String, String> dokumentmalGenerator, Function<String, String> fellesmalGenerator) {
        return super.medXSD(dokumentmalId -> dokumentmalGenerator.apply("v2."+ dokumentmalId), fellesmalGenerator);
    }

}

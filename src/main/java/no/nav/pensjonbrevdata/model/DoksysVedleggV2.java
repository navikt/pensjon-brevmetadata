package no.nav.pensjonbrevdata.model;


import java.util.function.Function;

public class DoksysVedleggV2 extends DoksysVedlegg {

    public DoksysVedleggV2(String vedleggkode, String dekode, String dokumentmalId, String dokumentmalFelleselementId) {
        super(vedleggkode, dekode, dokumentmalId, dokumentmalFelleselementId);
    }

    @Override
    public DoksysVedlegg medXSD(Function<String, String> dokumentmalGenerator, Function<String, String> fellesmalGenerator) {
        String dokumentmal = dokumentmalGenerator.apply("v2."+dokumentmalId);
        String dokumentmalFelleselement = fellesmalGenerator.apply(dokumentmalFelleselementId);
        return new DoksysVedlegg(vedleggkode, dekode, dokumentmalId, dokumentmalFelleselementId, dokumentmal, dokumentmalFelleselement);
    }
}
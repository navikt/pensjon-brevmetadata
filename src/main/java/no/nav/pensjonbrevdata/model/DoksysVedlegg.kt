package no.nav.pensjonbrevdata.model;

import kotlin.jvm.functions.Function1;

import java.util.function.Function;

public class DoksysVedlegg {
    protected final String vedleggkode;
    protected final String dekode;
    protected final String dokumentmalId;
    protected final String dokumentmalFelleselementId;
    protected final String dokumentmal;
    protected final String dokumentmalFelleselement;

    public DoksysVedlegg(String vedleggkode, String dekode, String dokumentmalId, String dokumentmalFelleselementId) {
        this(vedleggkode, dekode, dokumentmalId, dokumentmalFelleselementId, null, null);
    }

    protected DoksysVedlegg(String vedleggkode, String dekode, String dokumentmalId, String dokumentmalFelleselementId, String dokumentmal, String dokumentmalFelleselement) {
        this.vedleggkode = vedleggkode;
        this.dekode = dekode;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
        this.dokumentmal = dokumentmal;
        this.dokumentmalFelleselement = dokumentmalFelleselement;
    }

    public String getDekode() {
        return dekode;
    }

    public String getDokumentmalId() {
        return dokumentmalId;
    }

    public String getDokumentmalFelleselementId() {
        return dokumentmalFelleselementId;
    }

    public String getDokumentmal() {
        return dokumentmal;
    }

    public String getDokumentmalFelleselement() {
        return dokumentmalFelleselement;
    }

    public String getVedleggkode() {
        return vedleggkode;
    }

    public DoksysVedlegg medXSD(Function1<String, String> dokumentmalGenerator, Function1<String, String> fellesmalGenerator) {
        String dokumentmal = dokumentmalGenerator.invoke(dokumentmalId);
        String dokumentmalFelleselement = fellesmalGenerator.invoke(dokumentmalFelleselementId);
        return new DoksysVedlegg(vedleggkode, dekode, dokumentmalId, dokumentmalFelleselementId, dokumentmal, dokumentmalFelleselement);
    }
}

package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.model.codes.*;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Doksysbrev extends Brevdata {
    private List<DoksysVedlegg> vedleggListe;
    private String dokumentmalId;
    private String dokumentmalFelleselementId;
    private String dokumentmal;
    private String dokumentmalFelleselement;

    public Doksysbrev(String brevkodeInBrevsystem,
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
                      String dokumentmalId,
                      String dokumentmalFelleselementId,
                      List<DoksysVedlegg> vedleggListe) {
        this(brevkodeInBrevsystem,
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
                prioritet,
                vedleggListe,
                dokumentmalId,
                dokumentmalFelleselementId,
                null,
                null);
    }

    private Doksysbrev(String brevkodeIBrevsystem, boolean redigerbart, String dekode, BrevkategoriCode brevkategori,
                       DokumenttypeCode dokType,  List<SprakCode> sprak, Boolean visIPselv, BrevUtlandCode utland,
                       BrevregeltypeCode brevregeltype, BrevkravtypeCode brevkravtype, DokumentkategoriCode dokumentkategori,
                       Boolean synligForVeileder, BrevkontekstCode brevkontekst, Integer prioritet,
                       List<DoksysVedlegg> vedleggListe, String dokumentmalId, String dokumentmalFelleselementId,
                       String dokumentmal, String dokumentmalFelleselement) {
        super(brevkodeIBrevsystem, redigerbart, dekode, brevkategori, dokType, sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori, synligForVeileder, brevkontekst,  BrevsystemCode.DOKSYS, prioritet);
        this.vedleggListe = vedleggListe;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
        this.dokumentmal = dokumentmal;
        this.dokumentmalFelleselement = dokumentmalFelleselement;
    }

    public String getDokumentmalId() {
        return dokumentmalId;
    }

    public List<DoksysVedlegg> getVedleggListe() {
        return vedleggListe;
    }

    public String getDokumentmal() {
        return dokumentmal;
    }

    public String getDokumentmalFelleselement() {
        return dokumentmalFelleselement;
    }

    public String getDokumentmalFelleselementId() {
        return dokumentmalFelleselementId;
    }

    @Override
    public Brevdata medXSD(Function<String, String> dokumentmalGenerator, Function<String, String> fellesmalGenerator) {
        String dokumentmal = dokumentmalGenerator.apply(dokumentmalId);
        String fellesmal = fellesmalGenerator.apply(dokumentmalFelleselementId);
        List<DoksysVedlegg> vedleggListeMedXSD = vedleggListe == null ? null : vedleggListe.stream().map((vedlegg)-> vedlegg.medXSD(dokumentmalGenerator, fellesmalGenerator)).collect(Collectors.toList());
        return new Doksysbrev(getBrevkodeIBrevsystem(), isRedigerbart(), getDekode(), getBrevkategori(), getDokType(),
                getSprak(), getVisIPselv(), getUtland(), getBrevregeltype(), getBrevkravtype(), getDokumentkategori(),
                getSynligForVeileder(), getBrevkontekst(), getPrioritet(), vedleggListeMedXSD, dokumentmalId,
                dokumentmalFelleselementId, dokumentmal, fellesmal);
    }
}

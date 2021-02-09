package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.dto.BrevdataDTO;
import no.nav.pensjonbrevdata.dto.DoksysbrevDTO;
import no.nav.pensjonbrevdata.model.codes.*;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Doksysbrev extends Brevdata {
    protected Supplier<List<DoksysVedlegg>> vedleggListe;
    protected String dokumentmalId;
    protected String dokumentmalFelleselementId;
    protected String dokumentmal;
    protected String dokumentmalFelleselement;

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
                      Supplier<List<DoksysVedlegg>> vedleggListe) {
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

    protected Doksysbrev(String brevkodeIBrevsystem, boolean redigerbart, String dekode, BrevkategoriCode brevkategori,
                       DokumenttypeCode dokType,  List<SprakCode> sprak, Boolean visIPselv, BrevUtlandCode utland,
                       BrevregeltypeCode brevregeltype, BrevkravtypeCode brevkravtype, DokumentkategoriCode dokumentkategori,
                       Boolean synligForVeileder, BrevkontekstCode brevkontekst, Integer prioritet,
                       Supplier<List<DoksysVedlegg>> vedleggListe, String dokumentmalId, String dokumentmalFelleselementId,
                       String dokumentmal, String dokumentmalFelleselement) {
        super(brevkodeIBrevsystem, redigerbart, dekode, brevkategori, dokType, sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori, synligForVeileder, brevkontekst,  BrevsystemCode.DOKSYS, prioritet);
        this.vedleggListe = vedleggListe;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
        this.dokumentmal = dokumentmal;
        this.dokumentmalFelleselement = dokumentmalFelleselement;
    }

    @Override
    public Brevdata medXSD(Function<String, String> dokumentmalGenerator, Function<String, String> fellesmalGenerator) {
        String dokumentmal = dokumentmalGenerator.apply(dokumentmalId);
        String fellesmal = fellesmalGenerator.apply(dokumentmalFelleselementId);
        Supplier<List<DoksysVedlegg>> vedleggListeMedXSD = vedleggListe == null ? null : () -> vedleggListe.get().stream().map((vedlegg)-> vedlegg.medXSD(dokumentmalGenerator, fellesmalGenerator)).collect(Collectors.toList());
        return new Doksysbrev(getBrevkodeIBrevsystem(), isRedigerbart(), getDekode(), getBrevkategori(), getDokType(),
                getSprak(), getVisIPselv(), getUtland(), getBrevregeltype(), getBrevkravtype(), getDokumentkategori(),
                getSynligForVeileder(), getBrevkontekst(), getPrioritet(), vedleggListeMedXSD, dokumentmalId,
                dokumentmalFelleselementId, dokumentmal, fellesmal);
    }

    @Override
    public BrevdataDTO toDTO() {
        return new DoksysbrevDTO(getBrevkodeIBrevsystem(),isRedigerbart(),getDekode(),getBrevkategori(),getDokType(),getSprak(),
                getVisIPselv(),getUtland(),getBrevregeltype(),getBrevkravtype(),getDokumentkategori(),getSynligForVeileder(),
                getBrevkontekst(),getPrioritet(),getBrevsystem(),vedleggListe == null ? null: vedleggListe.get(),dokumentmalId,
                dokumentmalFelleselementId,dokumentmal,dokumentmalFelleselement);
    }
}

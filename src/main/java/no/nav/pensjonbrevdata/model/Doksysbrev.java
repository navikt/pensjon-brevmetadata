package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.helpers.XsdFileReader;
import no.nav.pensjonbrevdata.model.codes.*;

import java.io.*;
import java.util.List;

public class Doksysbrev extends Brev {
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
                      String dokumentmalId,
                      String dokumentmalFelleselementId,
                      List<DoksysVedlegg> vedleggListe) {
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
                brevkontekst);

        this.vedleggListe = vedleggListe;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
    }

    public String getDokumentmalId() {
        return dokumentmalId;
    }

    public void setDokumentmalId(String dokumentmalId) {
        this.dokumentmalId = dokumentmalId;
    }

    public List<DoksysVedlegg> getVedleggListe() {
        return vedleggListe;
    }

    public void setVedleggListe(List<DoksysVedlegg> vedleggListe) {
        this.vedleggListe = vedleggListe;
    }

    public String getDokumentmal() throws IOException {
        if (dokumentmal == null) {
//        DETTE FUNGERTE IKKE I DOCKER, MEN FUNGERTE UTENFOR DOCKER:
//        File dokumentmalFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
//        dokumentmal = new String(Files.readAllBytes(Paths.get(dokumentmalFile.getPath())));
            XsdFileReader fileReader = new XsdFileReader();
            dokumentmal = fileReader.read("xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
        }
        return dokumentmal;
    }

    public void setDokumentmal(String dokumentmal) {
        this.dokumentmal = dokumentmal;
    }

    public String getDokumentmalFelleselement() throws IOException {
        if (dokumentmalFelleselement == null) {
            //        DETTE FUNGERTE IKKE I DOCKER, MEN FUNGERTE UTENFOR DOCKER:
            //        File dokumentmalFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
            //        File dokumentMalFelleselementFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
            //        dokumentmalFelleselement = new String(Files.readAllBytes(Paths.get(dokumentMalFelleselementFile.getPath())));
            XsdFileReader fileReader = new XsdFileReader();
            dokumentmalFelleselement = fileReader.read("xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
        }
        return dokumentmalFelleselement;
    }

    public void setDokumentmalFelleselement(String dokumentmalFelleselement) {
        this.dokumentmalFelleselement = dokumentmalFelleselement;
    }

    public String getDokumentmalFelleselementId() {
        return dokumentmalFelleselementId;
    }

    public void setDokumentmalFelleselementId(String dokumentmalFelleselementId) {
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
    }
}

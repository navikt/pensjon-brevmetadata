package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.helpers.XsdFileReader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Doksysbrev extends Brev {
    private List<DoksysVedlegg> vedleggListe;
    private String dokumentmalId;
    private String dokumentmalFelleselementId;
    private String dokumentmal;
    private String dokumentmalFelleselement;

    public Doksysbrev(boolean redigerbart, String dekode, String kategori, String doktype, String dokumentmalId, String dokumentmalFelleselementId, List<DoksysVedlegg> vedleggListe, List<SprakCode> sprak) {
        super(redigerbart, dekode, kategori, doktype, sprak);

        this.vedleggListe = vedleggListe;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
    }

    public void generateDokumentmalFromFile() throws IOException {
//        DETTE FUNGERTE IKKE I DOCKER, MEN FUNGERTE UTENFOR DOCKER:
//        File dokumentmalFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
//        dokumentmal = new String(Files.readAllBytes(Paths.get(dokumentmalFile.getPath())));
//        File dokumentMalFelleselementFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
//        dokumentmalFelleselement = new String(Files.readAllBytes(Paths.get(dokumentMalFelleselementFile.getPath())));
        XsdFileReader fileReader = new XsdFileReader();
        dokumentmal = fileReader.read("xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
        dokumentmalFelleselement = fileReader.read("xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
        for (DoksysVedlegg vedlegg : vedleggListe) {
            vedlegg.generateDokumentmalFromFile();
        }
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

    public String getDokumentmal() {
        return dokumentmal;
    }

    public void setDokumentmal(String dokumentmal) {
        this.dokumentmal = dokumentmal;
    }

    public String getDokumentmalFelleselement() {
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

package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.helpers.XsdFileReader;

import java.io.File;
import java.io.IOException;

public class DoksysVedlegg {
    private String dekode;
    private String dokumentmalId;
    private String dokumentmalFelleselementId;
    private String dokumentmal;
    private String dokumentmalFelleselement;

    public DoksysVedlegg(String dekode, String dokumentmalId, String dokumentmalFelleselementId) {
        this.dekode = dekode;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
    }

    public void generateDokumentmalFromFile() throws IOException {
        XsdFileReader fileReader = new XsdFileReader();
        dokumentmal = fileReader.read("xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
        dokumentmalFelleselement = fileReader.read("xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
    }

    public String getDekode() {
        return dekode;
    }

    public void setDekode(String dekode) {
        this.dekode = dekode;
    }

    public String getDokumentmalId() {
        return dokumentmalId;
    }

    public void setDokumentmalId(String dokumentmalId) {
        this.dokumentmalId = dokumentmalId;
    }

    public String getDokumentmalFelleselementId() {
        return dokumentmalFelleselementId;
    }

    public void setDokumentmalFelleselementId(String dokumentmalFelleselementId) {
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
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
}
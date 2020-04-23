package no.nav.pensjonbrevdata.model;

import java.util.List;

public class Brev {
    private boolean redigerbart;
    private String dekode;
    private String kategori;
    private String dokType;
    private String xsd;
    private List<SprakCode> sprak;
    private List<Vedlegg> vedleggListe;

    public Brev(boolean redigerbart, String dekode, String kategori, String doktype, String xsd, List<Vedlegg> vedleggListe, List<SprakCode> sprak) {
        this.redigerbart = redigerbart;
        this.dekode = dekode;
        this.kategori = kategori;
        this.dokType = doktype;
        this.xsd = xsd;
        this.vedleggListe = vedleggListe;
        this.sprak = sprak;

    }

    public boolean isRedigerbart() {
        return redigerbart;
    }

    public void setRedigerbart(boolean redigerbart) {
        this.redigerbart = redigerbart;
    }

    public String getDekode() {
        return dekode;
    }

    public void setDekode(String dekode) {
        this.dekode = dekode;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDokType() {
        return dokType;
    }

    public void setDokType(String dokType) {
        this.dokType = dokType;
    }

    public String getXsd() {
        return xsd;
    }

    public void setXsd(String xsd) {
        this.xsd = xsd;
    }

    public List<SprakCode> getSprak() {
        return sprak;
    }

    public void setSprak(List<SprakCode> sprak) {
        this.sprak = sprak;
    }

    public List<Vedlegg> getVedleggListe() {
        return vedleggListe;
    }

    public void setVedleggListe(List<Vedlegg> vedleggListe) {
        this.vedleggListe = vedleggListe;
    }
}

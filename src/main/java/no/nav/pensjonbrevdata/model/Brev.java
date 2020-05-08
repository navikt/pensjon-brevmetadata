package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;

public abstract class Brev extends Brevdata {
    private boolean redigerbart;
    private String dekode;
    private BrevkategoriCode brevkategori;
    private DokumenttypeCode dokType;
    private List<SprakCode> sprak;
    private Boolean visIPselv;
    private BrevUtlandCode utland;
    private BrevregeltypeCode brevregeltype;
    private BrevkravtypeCode brevkravtype;
    private BrevkontekstCode brevkontekst;
    private DokumentkategoriCode dokumentkategori;
    private Boolean synligForVeileder;


    public Brev(String brevkodeInBrevsystem,
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
                BrevsystemCode brevsystem
    ) {
        super(brevkodeInBrevsystem, brevsystem);
        this.redigerbart = redigerbart;
        this.dekode = dekode;
        this.brevkategori = brevkategori;
        this.dokType = doktype;
        this.sprak = sprak;
        this.visIPselv = visIPselv;
        this.utland = utland;
        this.brevregeltype = brevregeltype;
        this.brevkravtype = brevkravtype;
        this.dokumentkategori = dokumentkategori;
        this.synligForVeileder = synligForVeileder;
        this.brevkontekst = brevkontekst;
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


    public List<SprakCode> getSprak() {
        return sprak;
    }

    public void setSprak(List<SprakCode> sprak) {
        this.sprak = sprak;
    }

    public BrevkategoriCode getBrevkategori() {
        return brevkategori;
    }

    public void setBrevkategori(BrevkategoriCode brevkategori) {
        this.brevkategori = brevkategori;
    }

    public DokumenttypeCode getDokType() {
        return dokType;
    }

    public void setDokType(DokumenttypeCode dokType) {
        this.dokType = dokType;
    }

    public Boolean getVisIPselv() {
        return visIPselv;
    }

    public void setVisIPselv(Boolean visIPselv) {
        this.visIPselv = visIPselv;
    }

    public BrevUtlandCode getUtland() {
        return utland;
    }

    public void setUtland(BrevUtlandCode utland) {
        this.utland = utland;
    }

    public BrevregeltypeCode getBrevregeltype() {
        return brevregeltype;
    }

    public void setBrevregeltype(BrevregeltypeCode brevregeltype) {
        this.brevregeltype = brevregeltype;
    }

    public BrevkravtypeCode getBrevkravtype() {
        return brevkravtype;
    }

    public void setBrevkravtype(BrevkravtypeCode brevkravtype) {
        this.brevkravtype = brevkravtype;
    }

    public BrevkontekstCode getBrevkontekst() {
        return brevkontekst;
    }

    public void setBrevkontekst(BrevkontekstCode brevkontekst) {
        this.brevkontekst = brevkontekst;
    }

    public DokumentkategoriCode getDokumentkategori() {
        return dokumentkategori;
    }

    public void setDokumentkategori(DokumentkategoriCode dokumentkategori) {
        this.dokumentkategori = dokumentkategori;
    }

    public Boolean getSynligForVeileder() {
        return synligForVeileder;
    }

    public void setSynligForVeileder(Boolean synligForVeileder) {
        this.synligForVeileder = synligForVeileder;
    }
}

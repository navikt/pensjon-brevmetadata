package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;

public abstract class Brevdata {
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
    private Integer prioritet;
    private String brevkodeIBrevsystem;
    private BrevsystemCode brevsystem;

    public Brevdata(String brevkodeIBrevsystem,
                    boolean redigerbart,
                    String dekode,
                    BrevkategoriCode brevkategori,
                    DokumenttypeCode dokType,
                    List<SprakCode> sprak,
                    Boolean visIPselv,
                    BrevUtlandCode utland,
                    BrevregeltypeCode brevregeltype,
                    BrevkravtypeCode brevkravtype,
                    DokumentkategoriCode dokumentkategori,
                    Boolean synligForVeileder,
                    BrevkontekstCode brevkontekst,
                    BrevsystemCode brevsystem,
                    Integer prioritet) {
        this.brevkodeIBrevsystem = brevkodeIBrevsystem;
        this.brevsystem = brevsystem;
        this.redigerbart = redigerbart;
        this.dekode = dekode;
        this.brevkategori = brevkategori;
        this.dokType = dokType;
        this.sprak = sprak;
        this.visIPselv = visIPselv;
        this.utland = utland;
        this.brevregeltype = brevregeltype;
        this.brevkravtype = brevkravtype;
        this.brevkontekst = brevkontekst;
        this.dokumentkategori = dokumentkategori;
        this.synligForVeileder = synligForVeileder;
        this.prioritet = prioritet;
    }

    public String getBrevkodeIBrevsystem() {
        return brevkodeIBrevsystem;
    }

    public BrevsystemCode getBrevsystem() {
        return brevsystem;
    }

    public List<SprakCode> getSprak() {
        return sprak;
    }

    public boolean isRedigerbart() {
        return redigerbart;
    }

    public String getDekode() {
        return dekode;
    }

    public BrevkategoriCode getBrevkategori() {
        return brevkategori;
    }

    public DokumenttypeCode getDokType() {
        return dokType;
    }

    public Boolean getVisIPselv() {
        return visIPselv;
    }

    public BrevUtlandCode getUtland() {
        return utland;
    }

    public BrevregeltypeCode getBrevregeltype() {
        return brevregeltype;
    }

    public BrevkravtypeCode getBrevkravtype() {
        return brevkravtype;
    }

    public BrevkontekstCode getBrevkontekst() {
        return brevkontekst;
    }

    public DokumentkategoriCode getDokumentkategori() {
        return dokumentkategori;
    }

    public Boolean getSynligForVeileder() {
        return synligForVeileder;
    }

    public Integer getPrioritet() {
        return prioritet;
    }
}

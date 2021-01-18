package no.nav.pensjonbrevdata.dto;

import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.DoksysVedlegg;
import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DoksysbrevDTO implements BrevdataDTO {
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
    private List<DoksysVedlegg> vedleggListe;
    private String dokumentmalId;
    private String dokumentmalFelleselementId;
    private String dokumentmal;
    private String dokumentmalFelleselement;

    public DoksysbrevDTO(String brevkodeIBrevsystem, boolean redigerbart, String dekode, BrevkategoriCode brevkategori,
                         DokumenttypeCode dokType, List<SprakCode> sprak, Boolean visIPselv, BrevUtlandCode utland,
                         BrevregeltypeCode brevregeltype, BrevkravtypeCode brevkravtype, DokumentkategoriCode dokumentkategori,
                         Boolean synligForVeileder, BrevkontekstCode brevkontekst, Integer prioritet, BrevsystemCode brevsystem,
                         List<DoksysVedlegg> vedleggListe, String dokumentmalId, String dokumentmalFelleselementId,
                         String dokumentmal, String dokumentmalFelleselement) {

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
    public boolean isRedigerbart() {
        return redigerbart;
    }

    @Override
    public String getDekode() {
        return dekode;
    }

    @Override
    public BrevkategoriCode getBrevkategori() {
        return brevkategori;
    }

    @Override
    public DokumenttypeCode getDokType() {
        return dokType;
    }

    @Override
    public List<SprakCode> getSprak() {
        return sprak;
    }

    @Override
    public Boolean getVisIPselv() {
        return visIPselv;
    }

    @Override
    public BrevUtlandCode getUtland() {
        return utland;
    }

    @Override
    public BrevregeltypeCode getBrevregeltype() {
        return brevregeltype;
    }

    @Override
    public BrevkravtypeCode getBrevkravtype() {
        return brevkravtype;
    }

    @Override
    public BrevkontekstCode getBrevkontekst() {
        return brevkontekst;
    }

    @Override
    public DokumentkategoriCode getDokumentkategori() {
        return dokumentkategori;
    }

    @Override
    public Boolean getSynligForVeileder() {
        return synligForVeileder;
    }

    @Override
    public Integer getPrioritet() {
        return prioritet;
    }

    @Override
    public String getBrevkodeIBrevsystem() {
        return brevkodeIBrevsystem;
    }

    @Override
    public BrevsystemCode getBrevsystem() {
        return brevsystem;
    }
}

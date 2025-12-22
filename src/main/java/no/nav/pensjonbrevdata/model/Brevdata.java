package no.nav.pensjonbrevdata.model;

import kotlin.jvm.functions.Function1;
import no.nav.pensjonbrevdata.dto.BrevdataDTO;
import no.nav.pensjonbrevdata.model.codes.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Brevdata {
    private final boolean redigerbart;
    private final String dekode;
    private final BrevkategoriCode brevkategori;
    private final DokumenttypeCode dokType;
    private final List<SprakCode> sprak;
    private final Boolean visIPselv;
    private final BrevUtlandCode utland;
    private final BrevregeltypeCode brevregeltype;
    private final BrevkravtypeCode brevkravtype;
    private final BrevkontekstCode brevkontekst;
    private final DokumentkategoriCode dokumentkategori;
    private final Boolean synligForVeileder;
    private final Integer prioritet;
    private final String brevkodeIBrevsystem;
    private final BrevsystemCode brevsystem;

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

    public abstract Brevdata medXSD(@NotNull Function1<String, String> dokumentmalGenerator, @NotNull Function1<String, String> fellesmalGenerator);

    public abstract BrevdataDTO toDTO();
}

package no.nav.pensjonbrevdata.dto;

import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;

public interface BrevdataDTO {
    boolean isRedigerbart();
    String getDekode();
    BrevkategoriCode getBrevkategori();
    DokumenttypeCode getDokType();
    List<SprakCode> getSprak();
    Boolean getVisIPselv();
    BrevUtlandCode getUtland();
    BrevregeltypeCode getBrevregeltype();
    BrevkravtypeCode getBrevkravtype();
    BrevkontekstCode getBrevkontekst();
    DokumentkategoriCode getDokumentkategori();
    Boolean getSynligForVeileder();
    Integer getPrioritet();
    String getBrevkodeIBrevsystem();
    BrevsystemCode getBrevsystem();
}

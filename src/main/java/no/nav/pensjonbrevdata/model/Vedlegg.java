package no.nav.pensjonbrevdata.model;

public class Vedlegg {
    private String xsd;
    private String dekode;

    public Vedlegg(String dekode, String xsd){
        this.dekode=dekode;
        this.xsd=xsd;
    }
}

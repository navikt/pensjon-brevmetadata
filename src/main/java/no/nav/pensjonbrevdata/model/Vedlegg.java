package no.nav.pensjonbrevdata.model;

public class Vedlegg {
    private String xsd;
    private String dekode;

    public Vedlegg(String dekode, String xsd){
        this.dekode=dekode;
        this.xsd=xsd;
    }

    public String getXsd() {
        return xsd;
    }

    public void setXsd(String xsd) {
        this.xsd = xsd;
    }

    public String getDekode() {
        return dekode;
    }

    public void setDekode(String dekode) {
        this.dekode = dekode;
    }


}

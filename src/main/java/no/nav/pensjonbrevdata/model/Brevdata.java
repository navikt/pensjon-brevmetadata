package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.model.codes.BrevsystemCode;

public abstract class Brevdata {
    private String brevkodeIBrevsystem;
    private BrevsystemCode brevsystem;

    public Brevdata(String brevkodeIBrevsystem, BrevsystemCode brevsystem) {
        this.brevkodeIBrevsystem = brevkodeIBrevsystem;
        this.brevsystem = brevsystem;
    }

    public String getBrevkodeIBrevsystem() {
        return brevkodeIBrevsystem;
    }

    public BrevsystemCode getBrevsystem() {
        return brevsystem;
    }
}

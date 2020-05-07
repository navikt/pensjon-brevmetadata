package no.nav.pensjonbrevdata.model;

public abstract class Brevdata {
    public String brevkodeInBrevsystem;

    public Brevdata(String brevkodeInBrevsystem) {
        this.brevkodeInBrevsystem = brevkodeInBrevsystem;
    }

    public String getBrevkodeInBrevsystem() {
        return brevkodeInBrevsystem;
    }

    public void setBrevkodeInBrevsystem(String brevkodeInBrevsystem) {
        this.brevkodeInBrevsystem = brevkodeInBrevsystem;
    }
}

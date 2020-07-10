package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.json.JSONVisitor;
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode;
import no.nav.pensjonbrevdata.json.JSONVisitable;

public abstract class Brevdata implements JSONVisitable {
    private String brevkodeIBrevsystem;
    private BrevsystemCode brevsystem;

    public Brevdata(String brevkodeIBrevsystem, BrevsystemCode brevsystem) {
        this.brevkodeIBrevsystem = brevkodeIBrevsystem;
        this.brevsystem = brevsystem;
    }

    public String getBrevkodeIBrevsystem() {
        return brevkodeIBrevsystem;
    }

    @Override
    public JSONVisitor visit(JSONVisitor jsonVisitor) {
        return jsonVisitor.field("brevkodeIBrevsystem",brevkodeIBrevsystem)
                .field("brevsystem", brevsystem);
    }
}

package no.nav.pensjonbrevdata.json;

public interface JSONVisitable {
    JSONVisitor visit(JSONVisitor jsonVisitor);
}

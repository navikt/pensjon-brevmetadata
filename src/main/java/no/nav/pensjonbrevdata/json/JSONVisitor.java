package no.nav.pensjonbrevdata.json;

public interface JSONVisitor extends JSONIfiable {
    JSONVisitor field(String fieldName, String fieldValue);
    JSONVisitor field(String fieldName, JSONIfiable fieldValue);
    JSONVisitor field(String fieldName, JSONList<?> fieldValues);
    JSONVisitor field(String fieldName, Boolean fieldValue);
    JSONVisitor field(String fieldName, Integer fieldValue);
}

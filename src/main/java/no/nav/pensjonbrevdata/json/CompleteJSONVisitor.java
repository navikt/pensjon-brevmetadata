package no.nav.pensjonbrevdata.json;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CompleteJSONVisitor implements JSONVisitor {
    private List<Pair<String, String>> fields = new ArrayList<>();

    public JSONVisitor field(String fieldName, String fieldValue) {
        return fieldIntern(fieldName, fieldValue==null?null:"\""+escape(fieldValue)+"\"");
    }

    private static String escape(String s){
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }

    private JSONVisitor fieldIntern(String fieldName, String fieldValue) {
        fields.add(new Pair<>(fieldName, fieldValue));
        return this;
    }

    public JSONVisitor field(String fieldName, Boolean fieldValue) {
        return fieldIntern(fieldName,fieldValue==null?null:fieldValue.toString());
    }

    public JSONVisitor field(String fieldName, Integer fieldValue) {
        return fieldIntern(fieldName,fieldValue==null?null:fieldValue.toString());
    }

    JSONVisitor field(String fieldName, JSONVisitable fieldValue) {
        return field(fieldName,fieldValue==null?null:fieldValue.visit(new CompleteJSONVisitor()));
    }

    public JSONVisitor field(String fieldName, JSONIfiable fieldValue) {
        return fieldIntern(fieldName,fieldValue==null?null:fieldValue.asJSON());
    }

    public JSONVisitor field(String fieldName, JSONList<?> fieldValues) {
        if(fieldValues==null) {
            return fieldIntern(fieldName, null);
        }
        return fieldIntern(fieldName, fieldValues.asJSON());
    }

    public String asJSON() {
        StringJoiner stringJoiner = new StringJoiner(",", "{", "}");
        fields.stream().map(field->"\""+field.getKey()+"\":"+field.getValue()).forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}

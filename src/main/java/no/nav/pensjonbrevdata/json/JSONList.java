package no.nav.pensjonbrevdata.json;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class JSONList<T> {
    private final Function<T, String> mapFunction;
    private final List<T> list;
    private JSONList(List<T> list, Function<T, String> mapFunction) {
        this.list = list;
        this.mapFunction = mapFunction;
    }

    public static JSONList<?> jsonVisitableList(List<? extends JSONVisitable> list) {
        return list==null?null:new JSONList<>(list, visitable -> visitable.visit(new CompleteJSONVisitor()).asJSON());
    }

    public static JSONList<?> jsonIfiableList(List<? extends JSONIfiable> list) {
        return list==null?null:new JSONList<>(list, JSONIfiable::asJSON);
    }

    public String asJSON() {
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        list.stream().map(mapFunction)
                .forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}

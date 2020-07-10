package no.nav.pensjonbrevdata.json;

import no.nav.pensjonbrevdata.json.JSONIfiable;

public interface JSONIfiableEnum extends JSONIfiable {
    default String asJSON() {
        return name();
    }

    String name();
}

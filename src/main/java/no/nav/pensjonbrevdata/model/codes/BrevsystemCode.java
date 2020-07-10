package no.nav.pensjonbrevdata.model.codes;

import no.nav.pensjonbrevdata.json.JSONIfiableEnum;

public enum BrevsystemCode implements JSONIfiableEnum {
    /**
     * Brev som skal bestilles i doksys
     */
    DOKSYS,
    /**
     * Brev som ikke bestilles i doksys
     */
    GAMMEL;
}

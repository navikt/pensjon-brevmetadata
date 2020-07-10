package no.nav.pensjonbrevdata.model.codes;

import no.nav.pensjonbrevdata.json.JSONIfiableEnum;

public enum BrevUtlandCode implements JSONIfiableEnum {
        /**
         * Brev vises for innland og utland
         */
        ALLTID,
        /**
         * Innlandsbrev
         */
        NASJONALT,
        /**
         * Utlandsbrev
         */
        UTLAND;
}

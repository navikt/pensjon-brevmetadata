package no.nav.pensjonbrevdata.model.codes;

import no.nav.pensjonbrevdata.json.JSONIfiableEnum;

public enum BrevkontekstCode implements JSONIfiableEnum {
        /**
         * Alle kontekster
         */
        ALLTID,
        /**
         * Sakskontekst
         */
        SAK,
        /**
         * Vedtakskontekst
         */
        VEDTAK;
}

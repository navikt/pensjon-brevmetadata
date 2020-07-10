package no.nav.pensjonbrevdata.model.codes;

import no.nav.pensjonbrevdata.json.JSONIfiableEnum;

public enum BrevkategoriCode implements JSONIfiableEnum {
        /**
         * Brev med skjema
         */
        BREV_MED_SKJEMA,
        /**
         * Informasjon
         */
        INFORMASJON,
        /**
         * Innhente opplysninger
         */
        INNHENTE_OPPL,
        /**
         * Notat
         */
        NOTAT,
        /**
         * &Oslash;vrig
         */
        OVRIG,
        /**
         * Varsel
         */
        VARSEL,
        /**
         * Vedtak
         */
        VEDTAK;

    }

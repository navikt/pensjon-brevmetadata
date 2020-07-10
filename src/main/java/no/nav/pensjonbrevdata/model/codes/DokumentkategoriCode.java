package no.nav.pensjonbrevdata.model.codes;

import no.nav.pensjonbrevdata.json.JSONIfiableEnum;

public enum DokumentkategoriCode implements JSONIfiableEnum {
        /**
         * Brev
         */
        B,
        /**
         * E-post
         */
        EP,
        /**
         * Elektronisk skjema
         */
        ES,
        /** E-blankett */
        E_BLANKETT,

        F,
        /**
         * Informasjonsbrev
         */
        IB,
        /**
         * Ikke tolkbart skjema
         */
        IS,
        /**
         * Konvertert fra elektronisk arkiv
         */
        KD,
        /**
         * Konvertert fra papirarkiv (skannet)
         */
        KM,
        /**
         * Konverterte data fra gammelt system
         */
        KS,
        /**
         * Strukturerte elektroniske dokumenter
         */
        SED,
        /**
         * Tolkbart skjema
         */
        TS,
        /**
         * Vedtaksbrev
         */
        VB;
}

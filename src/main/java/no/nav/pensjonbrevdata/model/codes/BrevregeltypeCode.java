package no.nav.pensjonbrevdata.model.codes;

public enum BrevregeltypeCode {
        /**
         * Gammelt regelverk
         */
        GG,
        /**
         * Nytt regelverk med gammel opptjening
         */
        GN,
        /**
         * Nytt regelverk
         */
        NN,
        /**
         * Overgangsordning med ny og gammel opptjening
         */
        ON,
        /**
         * &Oslash;vrige brev, ikke knyttet til gammelt eller nytt regelverk.
         */
        OVRIGE;
}

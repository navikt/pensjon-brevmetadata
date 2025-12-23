package no.nav.pensjonbrevdata.model.codes

enum class BrevregeltypeCode {
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
     * vrige brev, ikke knyttet til gammelt eller nytt regelverk.
     */
    OVRIGE
}

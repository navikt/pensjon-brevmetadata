package no.nav.pensjonbrevdata.model

class DoksysVedlegg private constructor(
    val vedleggkode: String?,
    val dekode: String?,
    val dokumentmalId: String,
    val dokumentmalFelleselementId: String,
    val dokumentmal: String?,
    val dokumentmalFelleselement: String?,
) {
    constructor(
        vedleggkode: String?,
        dekode: String?,
        dokumentmalId: String,
        dokumentmalFelleselementId: String,
    ) : this(vedleggkode, dekode, dokumentmalId, dokumentmalFelleselementId, null, null)

    fun medXSD(dokumentmalGenerator: (String) -> String, fellesmalGenerator: (String) -> String) = DoksysVedlegg(
        vedleggkode,
        dekode,
        dokumentmalId,
        dokumentmalFelleselementId,
        dokumentmalGenerator.invoke(dokumentmalId),
        fellesmalGenerator.invoke(dokumentmalFelleselementId)
    )
}

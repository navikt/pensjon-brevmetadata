package no.nav.pensjonbrevdata.model

import kotlin.Function1

class DoksysVedlegg protected constructor(
    val vedleggkode: String?,
    val dekode: String?,
    val dokumentmalId: String?,
    val dokumentmalFelleselementId: String?,
    val dokumentmal: String?,
    val dokumentmalFelleselement: String?
) {
    constructor(
        vedleggkode: String?,
        dekode: String?,
        dokumentmalId: String?,
        dokumentmalFelleselementId: String?
    ) : this(vedleggkode, dekode, dokumentmalId, dokumentmalFelleselementId, null, null)

    fun medXSD(
        dokumentmalGenerator: Function1<String?, String?>,
        fellesmalGenerator: Function1<String?, String?>
    ): DoksysVedlegg {
        val dokumentmal = dokumentmalGenerator.invoke(dokumentmalId)
        val dokumentmalFelleselement = fellesmalGenerator.invoke(dokumentmalFelleselementId)
        return DoksysVedlegg(
            vedleggkode,
            dekode,
            dokumentmalId,
            dokumentmalFelleselementId,
            dokumentmal,
            dokumentmalFelleselement
        )
    }
}

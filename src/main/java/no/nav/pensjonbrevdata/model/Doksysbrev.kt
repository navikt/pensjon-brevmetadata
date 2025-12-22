package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.dto.DoksysbrevDTO
import no.nav.pensjonbrevdata.model.codes.*
import java.util.function.Supplier
import java.util.stream.Collectors

open class Doksysbrev(
    brevkodeIBrevsystem: String?,
    redigerbart: Boolean,
    dekode: String?,
    brevkategori: BrevkategoriCode?,
    dokType: DokumenttypeCode?,
    sprak: List<SprakCode>?,
    visIPselv: Boolean?,
    utland: BrevUtlandCode?,
    brevregeltype: BrevregeltypeCode?,
    brevkravtype: BrevkravtypeCode?,
    dokumentkategori: DokumentkategoriCode?,
    synligForVeileder: Boolean?,
    brevkontekst: BrevkontekstCode?,
    prioritet: Int?,
    @JvmField protected val vedleggListe: Supplier<List<DoksysVedlegg?>?>?,
    @JvmField protected val dokumentmalId: String,
    @JvmField protected val dokumentmalFelleselementId: String,
    protected val dokumentmal: String?,
    protected val dokumentmalFelleselement: String?
) : Brevdata(
    brevkodeIBrevsystem,
    redigerbart,
    dekode,
    brevkategori,
    dokType,
    sprak,
    visIPselv,
    utland,
    brevregeltype,
    brevkravtype,
    dokumentkategori,
    synligForVeileder,
    brevkontekst,
    BrevsystemCode.DOKSYS,
    prioritet
) {
    constructor(
        brevkodeInBrevsystem: String?,
        redigerbart: Boolean,
        dekode: String?,
        brevkategori: BrevkategoriCode?,
        doktype: DokumenttypeCode?,
        sprak: List<SprakCode>?,
        visIPselv: Boolean?,
        utland: BrevUtlandCode?,
        brevregeltype: BrevregeltypeCode?,
        brevkravtype: BrevkravtypeCode?,
        dokumentkategori: DokumentkategoriCode?,
        synligForVeileder: Boolean?,
        brevkontekst: BrevkontekstCode?,
        prioritet: Int?,
        dokumentmalId: String,
        dokumentmalFelleselementId: String,
        vedleggListe: Supplier<List<DoksysVedlegg?>?>?
    ) : this(
        brevkodeInBrevsystem,
        redigerbart,
        dekode,
        brevkategori,
        doktype,
        sprak,
        visIPselv,
        utland,
        brevregeltype,
        brevkravtype,
        dokumentkategori,
        synligForVeileder,
        brevkontekst,
        prioritet,
        vedleggListe,
        dokumentmalId,
        dokumentmalFelleselementId,
        null,
        null
    )

    override fun medXSD(
        dokumentmalGenerator: (String) -> String,
        fellesmalGenerator: (String) -> String,
    ): Brevdata {
        val dokumentmal = dokumentmalGenerator.invoke(dokumentmalId)
        val fellesmal = fellesmalGenerator.invoke(dokumentmalFelleselementId)
        val vedleggListeMedXSD: Supplier<List<DoksysVedlegg?>?>? =
            if (vedleggListe == null) null else Supplier {
            vedleggListe.get()!!.stream().map<DoksysVedlegg> { vedlegg: DoksysVedlegg? ->
                vedlegg!!.medXSD(
                    dokumentmalGenerator,
                    fellesmalGenerator
                )
            }.collect(
                Collectors.toList()
            )
        }
        return Doksysbrev(
            brevkodeIBrevsystem, isRedigerbart, dekode, brevkategori, dokType,
            sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori,
            synligForVeileder, brevkontekst, prioritet, vedleggListeMedXSD, dokumentmalId,
            dokumentmalFelleselementId, dokumentmal, fellesmal
        )
    }

    override fun toDTO(): BrevdataDTO {
        return DoksysbrevDTO(
            brevkodeIBrevsystem,
            isRedigerbart,
            dekode,
            brevkategori,
            dokType,
            sprak,
            visIPselv,
            utland,
            brevregeltype,
            brevkravtype,
            dokumentkategori,
            synligForVeileder,
            brevkontekst,
            prioritet,
            brevsystem,
            if (vedleggListe == null) null else vedleggListe.get(),
            dokumentmalId,
            dokumentmalFelleselementId,
            dokumentmal,
            dokumentmalFelleselement
        )
    }
}

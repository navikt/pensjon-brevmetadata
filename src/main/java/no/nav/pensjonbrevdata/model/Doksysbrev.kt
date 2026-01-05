package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.dto.DoksysbrevDTO
import no.nav.pensjonbrevdata.model.codes.*
import java.util.function.Supplier
import java.util.stream.Collectors

open class Doksysbrev(
    brevkodeIBrevsystem: String,
    redigerbart: Boolean,
    dekode: String,
    brevkategori: BrevkategoriCode?,
    dokType: DokumenttypeCode,
    sprak: List<SprakCode>?,
    visIPselv: Boolean?,
    utland: BrevUtlandCode?,
    brevregeltype: BrevregeltypeCode?,
    brevkravtype: BrevkravtypeCode?,
    dokumentkategori: DokumentkategoriCode,
    synligForVeileder: Boolean?,
    brevkontekst: BrevkontekstCode?,
    prioritet: Int?,
    protected val dokumentmalId: String,
    protected val dokumentmalFelleselementId: String,
    protected val vedleggListe: Supplier<List<DoksysVedlegg>>?,
    protected val dokumentmal: String? = null,
    protected val dokumentmalFelleselement: String? = null
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
    override fun medXSD(
        dokumentmalGenerator: (String) -> String,
        fellesmalGenerator: (String) -> String,
    ): Brevdata {
        val dokumentmal = dokumentmalGenerator(dokumentmalId)
        val fellesmal = fellesmalGenerator(dokumentmalFelleselementId)
        val vedleggListeMedXSD: Supplier<List<DoksysVedlegg>>? =
            if (vedleggListe == null) null else Supplier {
            vedleggListe.get().stream().map<DoksysVedlegg> { vedlegg ->
                vedlegg!!.medXSD(
                    dokumentmalGenerator,
                    fellesmalGenerator
                )
            }.collect(
                Collectors.toList()
            )
        }
        return Doksysbrev(
            brevkodeIBrevsystem, redigerbart, dekode, brevkategori, dokType,
            sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori,
            synligForVeileder, brevkontekst, prioritet,  dokumentmalId,
            dokumentmalFelleselementId, vedleggListeMedXSD, dokumentmal, fellesmal
        )
    }

    override fun toDTO(): BrevdataDTO = DoksysbrevDTO(
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
        prioritet,
        brevsystem,
        vedleggListe?.get(),
        dokumentmalId,
        dokumentmalFelleselementId,
        dokumentmal,
        dokumentmalFelleselement
    )
}

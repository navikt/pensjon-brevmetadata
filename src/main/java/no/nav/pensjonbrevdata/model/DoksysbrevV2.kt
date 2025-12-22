package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.model.codes.*
import java.util.function.Supplier
import java.util.stream.Collectors

class DoksysbrevV2(
    brevkodeInBrevsystem: String,
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
) : Doksysbrev(
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
    dokumentmalId,
    dokumentmalFelleselementId,
    vedleggListe
) {
    override fun medXSD(
        dokumentmalGenerator: (String) -> String,
        fellesmalGenerator: (String) -> String
    ): Brevdata {
        val dokumentmal = dokumentmalGenerator.invoke("v2." + dokumentmalId)
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
            brevkodeIBrevsystem, redigerbart, dekode, brevkategori, dokType,
            sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori,
            synligForVeileder, brevkontekst, prioritet, vedleggListeMedXSD, dokumentmalId,
            dokumentmalFelleselementId, dokumentmal, fellesmal
        )
    }
}

package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.model.codes.*
import java.util.function.Supplier

class DoksysbrevV2(
    brevkodeInBrevsystem: String,
    redigerbart: Boolean,
    dekode: String,
    brevkategori: BrevkategoriCode?,
    doktype: DokumenttypeCode,
    sprak: List<SprakCode>?,
    visIPselv: Boolean?,
    utland: BrevUtlandCode?,
    brevregeltype: BrevregeltypeCode?,
    brevkravtype: BrevkravtypeCode?,
    dokumentkategori: DokumentkategoriCode,
    synligForVeileder: Boolean?,
    brevkontekst: BrevkontekstCode?,
    prioritet: Int?,
    dokumentmalId: String,
    dokumentmalFelleselementId: String,
    vedleggListe: Supplier<List<DoksysVedlegg>>?
) : Doksysbrev(
    brevkodeIBrevsystem = brevkodeInBrevsystem,
    redigerbart = redigerbart,
    dekode = dekode,
    brevkategori = brevkategori,
    dokType = doktype,
    sprak = sprak,
    visIPselv = visIPselv,
    utland = utland,
    brevregeltype = brevregeltype,
    brevkravtype = brevkravtype,
    dokumentkategori = dokumentkategori,
    synligForVeileder = synligForVeileder,
    brevkontekst = brevkontekst,
    prioritet = prioritet,
    vedleggListe = vedleggListe,
    dokumentmalId = dokumentmalId,
    dokumentmalFelleselementId = dokumentmalFelleselementId,
) {
    override fun medXSD(
        dokumentmalGenerator: (String) -> String,
        fellesmalGenerator: (String) -> String
    ): Brevdata {
        val dokumentmal = dokumentmalGenerator("v2.$dokumentmalId")
        val fellesmal = fellesmalGenerator(dokumentmalFelleselementId)
        val vedleggListeMedXSD = vedleggListe?.let { liste ->
            Supplier {
                liste.get().map { vedlegg -> vedlegg.medXSD(dokumentmalGenerator, fellesmalGenerator) }
            }
        }
        return Doksysbrev(
            brevkodeIBrevsystem, redigerbart, dekode, brevkategori, dokType,
            sprak, visIPselv, utland, brevregeltype, brevkravtype, dokumentkategori,
            synligForVeileder, brevkontekst, prioritet,  dokumentmalId,
            dokumentmalFelleselementId, vedleggListeMedXSD, dokumentmal, fellesmal
        )
    }
}

package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.model.codes.*

abstract class Brevdata(
    open val brevkodeIBrevsystem: String,
    open val isRedigerbart: Boolean,
    open val dekode: String?,
    open val brevkategori: BrevkategoriCode?,
    open val dokType: DokumenttypeCode?,
    open val sprak: List<SprakCode>?,
    open val visIPselv: Boolean?,
    open val utland: BrevUtlandCode?,
    open val brevregeltype: BrevregeltypeCode?,
    open val brevkravtype: BrevkravtypeCode?,
    open val dokumentkategori: DokumentkategoriCode?,
    open val synligForVeileder: Boolean?,
    open val brevkontekst: BrevkontekstCode?,
    open val brevsystem: BrevsystemCode,
    open val prioritet: Int?
) {
    abstract fun medXSD(
        dokumentmalGenerator: (String) -> String,
        fellesmalGenerator: (String) -> String
    ): Brevdata

    abstract fun toDTO(): BrevdataDTO
}

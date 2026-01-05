package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.model.codes.*

data class GammeltBrev(
    override val brevkodeIBrevsystem: String,
    override val redigerbart: Boolean,
    override val dekode: String,
    override val brevkategori: BrevkategoriCode?,
    override val dokType: DokumenttypeCode,
    override val sprak: List<SprakCode>?,
    override val visIPselv: Boolean?,
    override val utland: BrevUtlandCode?,
    override val brevregeltype: BrevregeltypeCode?,
    override val brevkravtype: BrevkravtypeCode?,
    override val dokumentkategori: DokumentkategoriCode,
    override val synligForVeileder: Boolean?,
    override val brevkontekst: BrevkontekstCode?,
    override val prioritet: Int?,
    val brevgruppe: String?,
    override val brevsystem: BrevsystemCode = BrevsystemCode.GAMMEL,
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
    brevsystem,
    prioritet
), BrevdataDTO {
    override fun medXSD(dokumentmalGenerator: (String) -> String, fellesmalGenerator: (String) -> String): Brevdata = this

    override fun toDTO(): BrevdataDTO = this
}

package no.nav.pensjonbrevdata.model

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.model.codes.*
import kotlin.Function1

class GammeltBrev(
    brevkodeInBrevsystem: String?,
    redigerbart: Boolean,
    dekode: String?,
    brevkategori: BrevkategoriCode?,
    doktype: DokumenttypeCode?,
    sprak: MutableList<SprakCode?>?,
    visIPselv: Boolean?,
    utland: BrevUtlandCode?,
    brevregeltype: BrevregeltypeCode?,
    brevkravtype: BrevkravtypeCode?,
    dokumentkategori: DokumentkategoriCode?,
    synligForVeileder: Boolean?,
    brevkontekst: BrevkontekstCode?,
    prioritet: Int?,
    val brevgruppe: String?
) : Brevdata(
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
    BrevsystemCode.GAMMEL,
    prioritet
), BrevdataDTO {
    override fun medXSD(
        dokumentmalGenerator: Function1<String?, String?>,
        fellesmalGenerator: Function1<String?, String?>
    ): Brevdata {
        return this
    }

    override fun toDTO(): BrevdataDTO {
        return this
    }
}

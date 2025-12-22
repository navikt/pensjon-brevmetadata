package no.nav.pensjonbrevdata.dto

import no.nav.pensjonbrevdata.model.codes.*

interface BrevdataDTO {
    val redigerbart: Boolean
    val dekode: String?
    val brevkategori: BrevkategoriCode?
    val dokType: DokumenttypeCode?
    val sprak: List<SprakCode>?
    val visIPselv: Boolean?
    val utland: BrevUtlandCode?
    val brevregeltype: BrevregeltypeCode?
    val brevkravtype: BrevkravtypeCode?
    val brevkontekst: BrevkontekstCode?
    val dokumentkategori: DokumentkategoriCode?
    val synligForVeileder: Boolean?
    val prioritet: Int?
    val brevkodeIBrevsystem: String?
    val brevsystem: BrevsystemCode?
}

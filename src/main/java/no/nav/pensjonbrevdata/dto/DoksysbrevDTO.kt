package no.nav.pensjonbrevdata.dto

import no.nav.pensjonbrevdata.model.DoksysVedlegg
import no.nav.pensjonbrevdata.model.codes.*

class DoksysbrevDTO(
    override val brevkodeIBrevsystem: String?,
    override val redigerbart: Boolean,
    override val dekode: String?,
    override val brevkategori: BrevkategoriCode?,
    override val dokType: DokumenttypeCode?,
    override val sprak: MutableList<SprakCode?>?,
    override val visIPselv: Boolean?,
    override val utland: BrevUtlandCode?,
    override val brevregeltype: BrevregeltypeCode?,
    override val brevkravtype: BrevkravtypeCode?,
    override val dokumentkategori: DokumentkategoriCode?,
    override val synligForVeileder: Boolean?,
    override val brevkontekst: BrevkontekstCode?,
    override val prioritet: Int?,
    override val brevsystem: BrevsystemCode?,
    val vedleggListe: MutableList<DoksysVedlegg?>?,
    val dokumentmalId: String?,
    val dokumentmalFelleselementId: String?,
    val dokumentmal: String?,
    val dokumentmalFelleselement: String?
) : BrevdataDTO

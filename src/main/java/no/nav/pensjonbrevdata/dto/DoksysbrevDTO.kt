package no.nav.pensjonbrevdata.dto

import no.nav.pensjonbrevdata.model.DoksysVedlegg
import no.nav.pensjonbrevdata.model.codes.*

class DoksysbrevDTO(
    private val brevkodeIBrevsystem: String?,
    private val redigerbart: Boolean,
    private val dekode: String?,
    private val brevkategori: BrevkategoriCode?,
    private val dokType: DokumenttypeCode?,
    private val sprak: MutableList<SprakCode?>?,
    private val visIPselv: Boolean?,
    private val utland: BrevUtlandCode?,
    private val brevregeltype: BrevregeltypeCode?,
    private val brevkravtype: BrevkravtypeCode?,
    private val dokumentkategori: DokumentkategoriCode?,
    private val synligForVeileder: Boolean?,
    private val brevkontekst: BrevkontekstCode?,
    private val prioritet: Int?,
    private val brevsystem: BrevsystemCode?,
    val vedleggListe: MutableList<DoksysVedlegg?>?,
    val dokumentmalId: String?,
    val dokumentmalFelleselementId: String?,
    val dokumentmal: String?,
    val dokumentmalFelleselement: String?
) : BrevdataDTO {
    override fun isRedigerbart(): Boolean {
        return redigerbart
    }

    override fun getDekode(): String? {
        return dekode
    }

    override fun getBrevkategori(): BrevkategoriCode? {
        return brevkategori
    }

    override fun getDokType(): DokumenttypeCode? {
        return dokType
    }

    override fun getSprak(): MutableList<SprakCode?>? {
        return sprak
    }

    override fun getVisIPselv(): Boolean? {
        return visIPselv
    }

    override fun getUtland(): BrevUtlandCode? {
        return utland
    }

    override fun getBrevregeltype(): BrevregeltypeCode? {
        return brevregeltype
    }

    override fun getBrevkravtype(): BrevkravtypeCode? {
        return brevkravtype
    }

    override fun getBrevkontekst(): BrevkontekstCode? {
        return brevkontekst
    }

    override fun getDokumentkategori(): DokumentkategoriCode? {
        return dokumentkategori
    }

    override fun getSynligForVeileder(): Boolean? {
        return synligForVeileder
    }

    override fun getPrioritet(): Int? {
        return prioritet
    }

    override fun getBrevkodeIBrevsystem(): String? {
        return brevkodeIBrevsystem
    }

    override fun getBrevsystem(): BrevsystemCode? {
        return brevsystem
    }
}

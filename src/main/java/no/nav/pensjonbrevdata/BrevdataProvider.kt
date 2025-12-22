package no.nav.pensjonbrevdata

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.codes.DokumentkategoriCode
import no.nav.pensjonbrevdata.model.codes.SprakCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.collections.map

@Service
class BrevdataProvider @Autowired constructor(
    private val brevdataMapper: BrevdataMapper,
    private val sakBrevMapper: SakBrevMapper
) {
    fun getSprakForBrevkode(brevkode: String): List<SprakCode>? = brevdataMapper.map(brevkode).sprak

    fun getBrevForBrevkode(brevkode: String): Brevdata = brevdataMapper.map(brevkode)

    fun getBrevdataForSaktype(saktype: String): List<Brevdata> = sakBrevMapper.map(saktype).map { brevdataMapper.map(it) }

    fun getBrevkoderForSaktype(saktype: String): List<String> = sakBrevMapper.map(saktype)

    val allBrev: List<Brevdata>
        get() = brevdataMapper.allBrevAsList

    fun getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem: String): List<String> = brevdataMapper.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem)

    val eblanketter: MutableList<Brevdata?>
        get() = brevdataMapper.allBrevAsList.stream()
            .filter { brev: Brevdata? -> brev!!.dokumentkategori == DokumentkategoriCode.E_BLANKETT }
            .toList()
}

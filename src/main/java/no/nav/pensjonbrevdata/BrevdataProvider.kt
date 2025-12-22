package no.nav.pensjonbrevdata

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.codes.DokumentkategoriCode
import no.nav.pensjonbrevdata.model.codes.SprakCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BrevdataProvider @Autowired constructor(
    private val brevdataMapper: BrevdataMapper,
    private val sakBrevMapper: SakBrevMapper
) {
    fun getSprakForBrevkode(brevkode: String?): List<SprakCode>? {
        return brevdataMapper.map(brevkode)!!.sprak
    }

    fun getBrevForBrevkode(brevkode: String?): Brevdata? {
        return brevdataMapper.map(brevkode)
    }

    fun getBrevdataForSaktype(saktype: String?): MutableList<Brevdata?> {
        return sakBrevMapper.map(saktype).stream()
            .map<Brevdata?> { brevkode: String? -> brevdataMapper.map(brevkode) }
            .collect(Collectors.toList())
    }

    fun getBrevkoderForSaktype(saktype: String?): MutableList<String?> {
        return sakBrevMapper.map(saktype)
    }

    val allBrev: List<Brevdata>
        get() = brevdataMapper.allBrevAsList

    fun getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem: String): List<String> {
        return brevdataMapper.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem)
    }

    val eblanketter: MutableList<Brevdata?>
        get() = brevdataMapper.allBrevAsList.stream()
            .filter { brev: Brevdata? -> brev!!.dokumentkategori == DokumentkategoriCode.E_BLANKETT }
            .toList()
}

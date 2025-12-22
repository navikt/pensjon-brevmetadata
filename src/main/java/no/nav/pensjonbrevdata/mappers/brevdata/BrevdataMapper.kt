package no.nav.pensjonbrevdata.mappers.brevdata

import no.nav.pensjonbrevdata.model.Brevdata
import org.springframework.stereotype.Service

@Service
class BrevdataMapper {
    private val brevMap = BrevdataMap()

    fun map(brevkode: String): Brevdata = brevMap.get().filter { it.key != "AFP_INNV_MAN" }[brevkode] ?: throw IllegalArgumentException("Brevkode $brevkode does not exist")

    val allBrevAsList: List<Brevdata>
        get() = brevMap.get().filter { it.key != "AFP_INNV_MAN" }.map { it.value }

    fun getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem: String?): List<String> = brevMap.get()
        .filter { it.key != "AFP_INNV_MAN" }
        .filter { it.value.brevkodeIBrevsystem == brevkodeIBrevsystem }
        .map { it.key }
}

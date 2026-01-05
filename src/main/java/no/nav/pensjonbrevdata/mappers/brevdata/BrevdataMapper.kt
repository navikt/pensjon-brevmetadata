package no.nav.pensjonbrevdata.mappers.brevdata

import no.nav.pensjonbrevdata.model.Brevdata

interface BrevdataMapper {
    fun map(brevkode: String): Brevdata
    val allBrevAsList: List<Brevdata>
    fun getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem: String): List<String>
}

class BrevdataMapperImpl(private val brevMap: BrevdataMap = BrevdataMap()) : BrevdataMapper {
    override fun map(brevkode: String): Brevdata = brevMap.get().filter { it.key != "AFP_INNV_MAN" }[brevkode]
        ?: throw IllegalArgumentException("Brevkode $brevkode does not exist")

    override val allBrevAsList: List<Brevdata>
        get() = brevMap.get().filter { it.key != "AFP_INNV_MAN" }.values.toList()

    override fun getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem: String): List<String> = brevMap.get()
        .filter { it.key != "AFP_INNV_MAN" }
        .filter { it.value.brevkodeIBrevsystem == brevkodeIBrevsystem }
        .map { it.key }
}

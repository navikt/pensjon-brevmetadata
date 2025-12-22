package no.nav.pensjonbrevdata.mappers.brevdata

import no.nav.pensjonbrevdata.model.Brevdata
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BrevdataMapper {
    private val brevMap = BrevdataMap()
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun map(brevkode: String): Brevdata = brevMap.get().filter { it.key != "AFP_INNV_MAN" }[brevkode] ?: throw IllegalArgumentException("Brevkode \"$brevkode\" does not exist")

    val allBrevAsList: MutableList<Brevdata>
        get() {
            val brevdataList: MutableList<Brevdata> = ArrayList()
            val filtrertBrevMap: Map<String, Brevdata> = brevMap.get().filter { it.key != "AFP_INNV_MAN" }

            for (brevdataCallable in filtrertBrevMap.values) {
                try {
                    brevdataList.add(brevdataCallable)
                } catch (e: IllegalArgumentException) {
                    logger.info("Illegal argument i getAllBrevAsList", e)
                }
            }
            return brevdataList
        }

    fun getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem: String?): List<String> {
        val brevkeys: MutableList<String> = ArrayList()
        val filtrertBrevMap: Map<String, Brevdata> = brevMap.get().filter { it.key != "AFP_INNV_MAN" }

        for (key in filtrertBrevMap.keys) {
            try {
                if (filtrertBrevMap[key]!!.brevkodeIBrevsystem == brevkodeIBrevsystem) {
                    brevkeys.add(key)
                }
            } catch (e: IllegalArgumentException) {
                logger.info("Illegal argument i getBrevKeysForBrevkodeIBrevsystem", e)
            }
        }
        return brevkeys
    }
}

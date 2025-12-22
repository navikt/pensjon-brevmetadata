package no.nav.pensjonbrevdata.helpers

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper

object BrevMetaData {
    @JvmStatic // TODO: Fjern denne når resten er over på kotlin også
    val brevTypeCodes: Set<String> = BrevdataMapper().allBrevAsList
        .map { it.brevkodeIBrevsystem!! }
        .toSet()
}

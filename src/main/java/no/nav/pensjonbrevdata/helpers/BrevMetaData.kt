package no.nav.pensjonbrevdata.helpers

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.model.Brevdata
import java.util.stream.Collectors

object BrevMetaData {
    @JvmStatic
    val brevTypeCodes: Set<String?>
        get() = BrevdataMapper().getAllBrevAsList().stream()
            .map<String?> { obj: Brevdata? -> obj!!.getBrevkodeIBrevsystem() }
            .collect(Collectors.toSet())
}

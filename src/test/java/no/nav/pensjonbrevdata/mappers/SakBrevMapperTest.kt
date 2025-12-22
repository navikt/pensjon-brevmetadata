package no.nav.pensjonbrevdata.mappers

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class SakBrevMapperTest {
    private var mapper = SakBrevMapper()

    @Test
    fun kunInfoP1ErInkludertAvRedigerbareDoksysbrev() {
        val brevdataMapper = BrevdataMapper()

        val alleRedigerBareDoksysBrev = brevdataMapper.allBrevAsList
            .filter { brev -> brev.brevsystem == BrevsystemCode.DOKSYS && brev.redigerbart }
            .map { it.brevkodeIBrevsystem }

        for (sakType in mapper.keySet()) {
            val sakBrev = mapper.map(sakType)

            // ingen redigerbare doksysbrev blir returnert for sak, bortsett fra "INFO_P1"
            val redigerbareDoksysbrevForSak = sakBrev
                .filter { o: String? -> alleRedigerBareDoksysBrev.contains(o) }
                .filter { brev: String? -> brev != "INFO_P1" }

            assertThat(redigerbareDoksysbrevForSak, `is`(Matchers.empty<String>()))
        }
    }
}
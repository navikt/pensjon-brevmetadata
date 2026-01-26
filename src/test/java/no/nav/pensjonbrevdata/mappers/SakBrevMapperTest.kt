package no.nav.pensjonbrevdata.mappers

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapperImpl
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SakBrevMapperTest {
    private var mapper = SakBrevMapper()

    @Test
    fun kunInfoP1ErInkludertAvRedigerbareDoksysbrev() {
        val brevdataMapper = BrevdataMapperImpl()

        val alleRedigerBareDoksysBrev = brevdataMapper.allBrevAsList
            .filter { brev -> brev.brevsystem == BrevsystemCode.DOKSYS && brev.redigerbart }
            .map { it.brevkodeIBrevsystem }

        for (sakType in mapper.keySet()) {
            val sakBrev = mapper.map(sakType)

            // ingen redigerbare doksysbrev blir returnert for sak, bortsett fra "INFO_P1"
            val redigerbareDoksysbrevForSak = sakBrev
                .filter { o: String? -> alleRedigerBareDoksysBrev.contains(o) }

            assertEquals(redigerbareDoksysbrevForSak, listOf())
        }
    }
}
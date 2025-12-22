package no.nav.pensjonbrevdata.mappers

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.stream.Collectors

class SakBrevMapperTest {
    private var mapper: SakBrevMapper? = null

    @BeforeEach
    fun setup() {
        mapper = SakBrevMapper()
    }

    @Test
    fun kunInfoP1ErInkludertAvRedigerbareDoksysbrev() {
        val brevdataMapper = BrevdataMapper()

        val alleRedigerBareDoksysBrev = brevdataMapper.allBrevAsList.stream()
            .filter { brev: Brevdata? -> brev!!.brevsystem == BrevsystemCode.DOKSYS && brev.redigerbart }
            .map<String?>(Brevdata::brevkodeIBrevsystem)
            .toList()

        for (sakType in mapper!!.keySet()) {
            val sakBrev = mapper!!.map(sakType)

            // ingen redigerbare doksysbrev blir returnert for sak, bortsett fra "INFO_P1"
            val redigerbareDoksysbrevForSak = sakBrev.stream()
                .filter { o: String? -> alleRedigerBareDoksysBrev.contains(o) }
                .filter { brev: String? -> brev != "INFO_P1" }
                .collect(Collectors.toList())

            MatcherAssert.assertThat<List<String>>(
                redigerbareDoksysbrevForSak,
                Matchers.`is`<Collection<String>>(Matchers.empty<String>())
            )
        }
    }
}
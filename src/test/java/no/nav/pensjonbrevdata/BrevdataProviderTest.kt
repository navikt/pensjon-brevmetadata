package no.nav.pensjonbrevdata

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.GammeltBrev
import no.nav.pensjonbrevdata.model.codes.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection.empty
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BrevdataProviderTest {
    @MockK
    private lateinit var brevdataMapperMock: BrevdataMapper

    @MockK
    private lateinit var sakBrevMapperMock: SakBrevMapper

    private lateinit var provider: BrevdataProvider

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        provider = BrevdataProvider(brevdataMapperMock, sakBrevMapperMock)
    }

    @Test
    fun shouldGetListOfBrevKeysWhenGetBrevkeysForBrevkodeIBrevsystem() {
        val brevkeys = listOf("PE_AF_04_001", "PE_AF_04_003")
        every { brevdataMapperMock.getBrevKeysForBrevkodeIBrevsystem("TEST") } returns brevkeys

        val brevkeysReturned: List<String> = provider.getBrevKeysForBrevkodeIBrevsystem("TEST")

        assertThat(brevkeysReturned.size, `is`(2))
        assertThat(brevkeysReturned.contains("PE_AF_04_001"), `is`(true))
        assertThat(brevkeysReturned.contains("PE_AF_04_002"), `is`(false))
        assertThat(brevkeysReturned.contains("PE_AF_04_003"), `is`(true))
    }

    @Test
    fun shouldGetListOfSprakWhenGetSprakForBrevkode() {
        val brevkode = "TEST"
        val brev: Brevdata = GammeltBrev(
            "PE_AF_04_001",
            true,
            "Vedtak - innvilgelse av AFP",
            BrevkategoriCode.VEDTAK,
            DokumenttypeCode.U,
            listOf(SprakCode.NB, SprakCode.NN),
            true,
            BrevUtlandCode.NASJONALT,
            BrevregeltypeCode.GG,
            BrevkravtypeCode.ALLE,
            DokumentkategoriCode.VB,
            null,
            BrevkontekstCode.VEDTAK,
            null,
            "brevgr002",
            BrevsystemCode.GAMMEL
        )
        every { brevdataMapperMock.map(brevkode) } returns brev

        val actualListOfSprakCodes = provider.getSprakForBrevkode(brevkode)

        assertThat(actualListOfSprakCodes, `is`<List<SprakCode>?>(brev.sprak))
    }

    @Test
    fun onlyEblanketterIsReturned() {
        val eblanketter = BrevdataProvider(BrevdataMapper(), sakBrevMapperMock).eblanketter
        assertThat(eblanketter, not(empty<Brevdata>()))
        assertTrue(eblanketter.all { it.dokumentkategori == DokumentkategoriCode.E_BLANKETT })
    }
}
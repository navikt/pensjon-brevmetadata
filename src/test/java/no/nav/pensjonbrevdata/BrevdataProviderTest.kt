package no.nav.pensjonbrevdata

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.GammeltBrev
import no.nav.pensjonbrevdata.model.codes.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsEmptyCollection
import org.junit.jupiter.api.Assertions
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

        MatcherAssert.assertThat<Int?>(brevkeysReturned.size, CoreMatchers.`is`<Int?>(2))
        MatcherAssert.assertThat<Boolean?>(brevkeysReturned.contains("PE_AF_04_001"), CoreMatchers.`is`<Boolean?>(true))
        MatcherAssert.assertThat<Boolean?>(
            brevkeysReturned.contains("PE_AF_04_002"),
            CoreMatchers.`is`<Boolean?>(false)
        )
        MatcherAssert.assertThat<Boolean?>(brevkeysReturned.contains("PE_AF_04_003"), CoreMatchers.`is`<Boolean?>(true))
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

        MatcherAssert.assertThat<List<SprakCode>?>(
            actualListOfSprakCodes,
            CoreMatchers.`is`<List<SprakCode>?>(brev.sprak)
        )
    }

    @Test
    fun onlyEblanketterIsReturned() {
        val eblanketter = BrevdataProvider(BrevdataMapper(), sakBrevMapperMock).eblanketter
        MatcherAssert.assertThat<MutableList<Brevdata?>?>(
            eblanketter,
            CoreMatchers.not<MutableCollection<out Brevdata?>?>(IsEmptyCollection.empty<Brevdata?>())
        )
        Assertions.assertTrue(
            eblanketter.stream().allMatch { b: Brevdata? -> b!!.dokumentkategori == DokumentkategoriCode.E_BLANKETT })
    }
}
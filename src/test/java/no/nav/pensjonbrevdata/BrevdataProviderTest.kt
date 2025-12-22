package no.nav.pensjonbrevdata

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
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Arrays

@ExtendWith(MockitoExtension::class)
class BrevdataProviderTest {
    @Mock
    private val brevdataMapperMock: BrevdataMapper? = null

    @Mock
    private val sakBrevMapperMock: SakBrevMapper? = null

    private var provider: BrevdataProvider? = null

    @BeforeEach
    fun setup() {
        provider = BrevdataProvider(brevdataMapperMock!!, sakBrevMapperMock!!)
    }

    @Test
    fun shouldGetListOfBrevKeysWhenGetBrevkeysForBrevkodeIBrevsystem() {
        val brevkeys = listOf("PE_AF_04_001", "PE_AF_04_003")
        Mockito.`when`<List<String>>(brevdataMapperMock!!.getBrevKeysForBrevkodeIBrevsystem("TEST"))
            .thenReturn(brevkeys)

        val brevkeysReturned: List<String> = provider!!.getBrevKeysForBrevkodeIBrevsystem("TEST")

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
            Arrays.asList<SprakCode?>(SprakCode.NB, SprakCode.NN),
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
        Mockito.`when`<Brevdata?>(brevdataMapperMock!!.map(brevkode)).thenReturn(brev)

        val actualListOfSprakCodes = provider!!.getSprakForBrevkode(brevkode)

        MatcherAssert.assertThat<MutableList<SprakCode?>?>(
            actualListOfSprakCodes,
            CoreMatchers.`is`<MutableList<SprakCode?>?>(brev.sprak)
        )
    }

    @Test
    fun onlyEblanketterIsReturned() {
        val eblanketter = BrevdataProvider(BrevdataMapper(), sakBrevMapperMock!!).eblanketter
        MatcherAssert.assertThat<MutableList<Brevdata?>?>(
            eblanketter,
            CoreMatchers.not<MutableCollection<out Brevdata?>?>(IsEmptyCollection.empty<Brevdata?>())
        )
        Assertions.assertTrue(
            eblanketter.stream().allMatch { b: Brevdata? -> b!!.dokumentkategori == DokumentkategoriCode.E_BLANKETT })
    }
}
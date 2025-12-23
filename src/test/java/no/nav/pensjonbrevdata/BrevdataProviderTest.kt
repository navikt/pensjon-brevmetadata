package no.nav.pensjonbrevdata

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapperImpl
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.GammeltBrev
import no.nav.pensjonbrevdata.model.codes.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BrevdataProviderTest {
    private val brevdataMapperMock: BrevdataMapper = mockk()

    private val sakBrevMapperMock: SakBrevMapper = mockk()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun shouldGetListOfBrevKeysWhenGetBrevkeysForBrevkodeIBrevsystem() {
        val brevkeys = listOf("PE_AF_04_001", "PE_AF_04_003")

        every { brevdataMapperMock.getBrevKeysForBrevkodeIBrevsystem("TEST") } returns brevkeys

        val brevkeysReturned: List<String> = BrevdataProvider(brevdataMapperMock, sakBrevMapperMock).getBrevKeysForBrevkodeIBrevsystem("TEST")

        assertEquals(brevkeysReturned.size, 2)
        assertEquals(brevkeysReturned.contains("PE_AF_04_001"), true)
        assertEquals(brevkeysReturned.contains("PE_AF_04_002"), false)
        assertEquals(brevkeysReturned.contains("PE_AF_04_003"), true)
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

        val actualListOfSprakCodes = BrevdataProvider(brevdataMapperMock, sakBrevMapperMock).getSprakForBrevkode(brevkode)

        assertEquals(actualListOfSprakCodes, brev.sprak)
    }

    @Test
    fun onlyEblanketterIsReturned() {
        val eblanketter = BrevdataProvider(BrevdataMapperImpl(), sakBrevMapperMock).eblanketter
        assertNotEquals(eblanketter, listOf())
        assertTrue(eblanketter.all { it.dokumentkategori == DokumentkategoriCode.E_BLANKETT })
    }
}
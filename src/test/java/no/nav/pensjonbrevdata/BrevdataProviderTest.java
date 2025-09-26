package no.nav.pensjonbrevdata;

import io.getunleash.FakeUnleash;
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.Doksysbrev;
import no.nav.pensjonbrevdata.model.GammeltBrev;
import no.nav.pensjonbrevdata.model.codes.*;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrevdataProviderTest {
    static private final FakeUnleash unleash = new FakeUnleash();

    @Mock
    private BrevdataMapper brevdataMapperMock;

    @Mock
    private SakBrevMapper sakBrevMapperMock;

    @Mock
    private Doksysbrev doksysbrevMock;

    @Mock
    private GammeltBrev gammeltBrevMock;

    private BrevdataProvider provider;

    @BeforeAll
    static public void setupForAll() {
        UnleashProvider.initialize(unleash);
    }

    @BeforeEach
    public void setup() {
        provider = new BrevdataProvider();
        provider.setBrevdataMapper(brevdataMapperMock);
        provider.setSakBrevMapper(sakBrevMapperMock);
    }

    @Test
    public void shouldGetListOfBrevKeysWhenGetBrevkeysForBrevkodeIBrevsystem() {
        List<String> brevkeys = Arrays.asList("PE_AF_04_001","PE_AF_04_003");
        when(brevdataMapperMock.getBrevKeysForBrevkodeIBrevsystem("TEST")).thenReturn(brevkeys);

        List<String> brevkeysReturned = provider.getBrevKeysForBrevkodeIBrevsystem("TEST");

        assertThat(brevkeysReturned.size(), is(2));
        assertThat(brevkeysReturned.contains("PE_AF_04_001"), is(true));
        assertThat(brevkeysReturned.contains("PE_AF_04_002"), is(false));
        assertThat(brevkeysReturned.contains("PE_AF_04_003"), is(true));
    }

    @Test
    public void shouldGetListOfSprakWhenGetSprakForBrevkode() {
        String brevkode = "TEST";
        Brevdata brev = new GammeltBrev("PE_AF_04_001",
                        true,
                        "Vedtak - innvilgelse av AFP",
                        BrevkategoriCode.VEDTAK,
                        DokumenttypeCode.U,
                        Arrays.asList(SprakCode.NB, SprakCode.NN),
                        true,
                        BrevUtlandCode.NASJONALT,
                        BrevregeltypeCode.GG,
                        BrevkravtypeCode.ALLE,
                        DokumentkategoriCode.VB,
                        null,
                        BrevkontekstCode.VEDTAK,
                        null,
                        "brevgr002");
        when(brevdataMapperMock.map(brevkode)).thenReturn(brev);

        List<SprakCode> actualListOfSprakCodes = provider.getSprakForBrevkode(brevkode);

        assertThat(actualListOfSprakCodes, is(brev.getSprak()));
    }

    @Test
    public void onlyEblanketterIsReturned() {
        provider.setBrevdataMapper(new BrevdataMapper());

        List<Brevdata> eblanketter = provider.getEblanketter();
        assertThat(eblanketter, not(empty()));
        assertTrue(eblanketter.stream().allMatch(b -> b.getDokumentkategori().equals(DokumentkategoriCode.E_BLANKETT)));
    }
}
package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.Doksysbrev;
import no.nav.pensjonbrevdata.model.GammeltBrev;
import no.nav.pensjonbrevdata.model.codes.BrevUtlandCode;
import no.nav.pensjonbrevdata.model.codes.BrevkategoriCode;
import no.nav.pensjonbrevdata.model.codes.BrevkontekstCode;
import no.nav.pensjonbrevdata.model.codes.BrevkravtypeCode;
import no.nav.pensjonbrevdata.model.codes.BrevregeltypeCode;
import no.nav.pensjonbrevdata.model.codes.DokumentkategoriCode;
import no.nav.pensjonbrevdata.model.codes.DokumenttypeCode;
import no.nav.pensjonbrevdata.model.codes.SprakCode;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import no.finn.unleash.FakeUnleash;

@ExtendWith(MockitoExtension.class)
public class BrevdataProviderTest {
    static private FakeUnleash unleash = new FakeUnleash();

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
}
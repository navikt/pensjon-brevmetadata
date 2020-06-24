package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brev;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

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
        UnleashProvider.initialize(new FakeUnleash());
    }

    @BeforeEach
    public void setup() {
        provider = new BrevdataProvider();
        provider.setBrevdataMapper(brevdataMapperMock);
        provider.setSakBrevMapper(sakBrevMapperMock);
    }

    @Test
    public void shouldGetBrevForBrevkodeWithXsdWhenGetBrevForBrevkode() throws Exception {
        String brevkode = "TEST";
        when(brevdataMapperMock.map(brevkode)).thenReturn(doksysbrevMock);

        Brevdata brev = provider.getBrevForBrevkode(brevkode);

        verify(doksysbrevMock, times(1)).generateDokumentmalFromFile();

        assertThat(brev, is(doksysbrevMock));
    }

    @Test
    public void shouldReturnBrevdataForSaktypeWithXsdWhenGetBrevdataForSaktypeAndIncludeXsdTrue() throws Exception {
        String sak = "TESTSAK";
        List<String> brevkoder = Arrays.asList("DOKSYS1", "GAMMEL1", "GAMMEL2", "DOKSYS2", "GAMMEL3");
        when(sakBrevMapperMock.map(sak)).thenReturn(brevkoder);
        when(brevdataMapperMock.map(brevkoder.get(0))).thenReturn(doksysbrevMock);
        when(brevdataMapperMock.map(brevkoder.get(1))).thenReturn(gammeltBrevMock);
        when(brevdataMapperMock.map(brevkoder.get(2))).thenReturn(gammeltBrevMock);
        when(brevdataMapperMock.map(brevkoder.get(3))).thenReturn(doksysbrevMock);
        when(brevdataMapperMock.map(brevkoder.get(4))).thenReturn(gammeltBrevMock);

        List<Brevdata> result = provider.getBrevdataForSaktype(sak, true);

        verify(doksysbrevMock, times(2)).generateDokumentmalFromFile();
        assertThat(result.size(), is(brevkoder.size()));
    }

    @Test
    public void shouldReturnBrevdataForSaktypeWithOutXsdWhenGetBrevdataForSaktypeAndIncludeXsdFalse() throws Exception {
        String sak = "TESTSAK";
        List<String> brevkoder = Arrays.asList("DOKSYS1", "GAMMEL1", "GAMMEL2", "DOKSYS2", "GAMMEL3");
        when(sakBrevMapperMock.map(sak)).thenReturn(brevkoder);
        when(brevdataMapperMock.map(brevkoder.get(0))).thenReturn(doksysbrevMock);
        when(brevdataMapperMock.map(brevkoder.get(1))).thenReturn(gammeltBrevMock);
        when(brevdataMapperMock.map(brevkoder.get(2))).thenReturn(gammeltBrevMock);
        when(brevdataMapperMock.map(brevkoder.get(3))).thenReturn(doksysbrevMock);
        when(brevdataMapperMock.map(brevkoder.get(4))).thenReturn(gammeltBrevMock);

        List<Brevdata> result = provider.getBrevdataForSaktype(sak, false);

        verify(doksysbrevMock, times(0)).generateDokumentmalFromFile();
        assertThat(result.size(), is(brevkoder.size()));
    }

    @Test
    public void shouldReturnAllBrevWithXsdWhenGetAllBrevAndIncludeXsdTrue() throws Exception {
        List<Brevdata> returnedBrevList = Arrays.asList(doksysbrevMock, gammeltBrevMock, doksysbrevMock, doksysbrevMock, gammeltBrevMock);

        when(brevdataMapperMock.getAllBrevAsList()).thenReturn(returnedBrevList);

        List<Brevdata> result = provider.getAllBrev(true);

        verify(doksysbrevMock, times(3)).generateDokumentmalFromFile();
        assertThat(result.size(), is(returnedBrevList.size()));
    }

    @Test
    public void shouldReturnAllBrevWithOutXsdWhenGetAllBrevAndIncludeXsdFalse() throws Exception {
        List<Brevdata> returnedBrevList = Arrays.asList(doksysbrevMock, gammeltBrevMock, doksysbrevMock, doksysbrevMock, gammeltBrevMock);

        when(brevdataMapperMock.getAllBrevAsList()).thenReturn(returnedBrevList);

        List<Brevdata> result = provider.getAllBrev(false);

        verify(doksysbrevMock, times(0)).generateDokumentmalFromFile();
        assertThat(result.size(), is(returnedBrevList.size()));
    }

    @Test
    public void shouldGetListOfBrevKeysWhenGetBrevkeysForBrevkodeIBrevsystem() throws Exception {
        List<String> brevkeys = Arrays.asList("PE_AF_04_001","PE_AF_04_003");
        when(brevdataMapperMock.getBrevKeysForBrevkodeIBrevsystem("TEST")).thenReturn(brevkeys);

        List<String> brevkeysReturned = provider.getBrevKeysForBrevkodeIBrevsystem("TEST");

        assertThat(brevkeysReturned.size(), is(2));
        assertThat(brevkeysReturned.contains("PE_AF_04_001"), is(true));
        assertThat(brevkeysReturned.contains("PE_AF_04_002"), is(false));
        assertThat(brevkeysReturned.contains("PE_AF_04_003"), is(true));
    }

    @Test
    public void shouldGetListOfSprakWhenGetSprakForBrevkode() throws Exception {
        String brevkode = "TEST";
        Brev brev = new GammeltBrev("PE_AF_04_001",
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
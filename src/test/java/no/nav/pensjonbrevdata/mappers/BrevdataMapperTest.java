package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.model.Brevdata;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class BrevdataMapperTest {
    private BrevdataMapper mapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        mapper = new BrevdataMapper();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownBrevkode() throws Exception {
        String invalidBrevkode = "UGYLDIG_BREVKODE";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Brevkode \"" + invalidBrevkode + "\" does not exist");

        mapper.map(invalidBrevkode);
    }
}
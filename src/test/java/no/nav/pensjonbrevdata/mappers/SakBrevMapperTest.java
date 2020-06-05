package no.nav.pensjonbrevdata.mappers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SakBrevMapperTest {
    private SakBrevMapper mapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        mapper = new SakBrevMapper();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownSaktype() throws Exception {
        String invalidBrevkode = "UGYLDIG_SAKTYPE";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Saktype \"" + invalidBrevkode + "\" does not exist");

        mapper.map(invalidBrevkode);
    }

}
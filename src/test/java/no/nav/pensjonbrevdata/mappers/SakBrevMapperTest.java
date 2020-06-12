package no.nav.pensjonbrevdata.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SakBrevMapperTest {
    private SakBrevMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new SakBrevMapper();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownSaktype() {
        String invalidBrevkode = "UGYLDIG_SAKTYPE";

        assertThrows(IllegalArgumentException.class, () -> mapper.map(invalidBrevkode), "Saktype \"" + invalidBrevkode + "\" does not exist");
    }

}
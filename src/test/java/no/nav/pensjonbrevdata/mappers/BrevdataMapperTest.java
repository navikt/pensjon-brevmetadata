package no.nav.pensjonbrevdata.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class BrevdataMapperTest {
    private BrevdataMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new BrevdataMapper();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownBrevkode() {
        String invalidBrevkode = "UGYLDIG_BREVKODE";

        assertThrows(IllegalArgumentException.class, () -> mapper.map(invalidBrevkode),"Brevkode \"" + invalidBrevkode + "\" does not exist");
    }
}
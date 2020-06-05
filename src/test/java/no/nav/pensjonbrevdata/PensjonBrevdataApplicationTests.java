package no.nav.pensjonbrevdata;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import no.finn.unleash.Unleash;

@SpringBootTest
class PensjonBrevdataApplicationTests {

    @MockBean
    private Unleash defaultUnleash;

    @Test
    void contextLoads() {
    }

}

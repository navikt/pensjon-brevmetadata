package no.nav.pensjonbrevdata.unleash;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import no.finn.unleash.Unleash;
import no.finn.unleash.repository.ToggleFetcher;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UnleashBeanTest {
    private static final String ENDPOINT_URL = "http://127.0.0.1:9998";

    private UnleashBean unleashConsumerService;

    @Mock
    private ToggleFetcher toggleFetcher;

    @Mock
    private Unleash unleash;

    @BeforeEach
    public void setup() {
        unleashConsumerService = new UnleashBean();
        unleashConsumerService.setUnleash(unleash);
        unleashConsumerService.setToggleFetcher(toggleFetcher);
        unleashConsumerService.setEndpointUrl(ENDPOINT_URL);
    }

    @Test
    public void shouldReturnTrue() {
        when(unleash.isEnabled(any(String.class))).thenReturn(true);
        assertThat(unleashConsumerService.isEnabled("returnTrue"), is(true));
        verify(unleash).isEnabled(any());
    }

    @Test
    public void shouldReturnFalse() {
        when(unleash.isEnabled(any(String.class))).thenReturn(false);
        assertThat(unleashConsumerService.isEnabled("returnFalse"), is(false));
        verify(unleash).isEnabled(any());
    }
}
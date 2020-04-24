package no.nav.pensjonbrevdata.unleash;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import no.finn.unleash.Unleash;
import no.finn.unleash.repository.ToggleFetcher;

@RunWith(MockitoJUnitRunner.class)
public class UnleashBeanTest {
    private static final String ENDPOINT_URL = "http://127.0.0.1:9998";

    private UnleashBean unleashConsumerService;

    @Mock
    private ToggleFetcher toggleFetcher;

    @Mock
    private Unleash unleash;

    @Before
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
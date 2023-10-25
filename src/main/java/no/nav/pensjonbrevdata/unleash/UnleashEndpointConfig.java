package no.nav.pensjonbrevdata.unleash;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UnleashEndpointConfig {

    @Bean
    public Unleash defaultUnleash(
            @Value("${UNLEASH_SERVER_API_URL}") String endpoint,
            @Value("${UNLEASH_SERVER_API_TOKEN}") String token,
            @Value("${unleash.toggle.interval}") Long togglesInterval,
            @Value("${NAIS_APP_NAME}") String appName,
            @Value("${ENVIRONMENT_NAME}") String environmentName
    ) {
        DefaultUnleash unleash = new DefaultUnleash(
                UnleashConfig.builder()
                        .appName(appName)
                        .environment(environmentName)
                        .fetchTogglesInterval(togglesInterval)
                        .unleashAPI(endpoint + "/api")
                        .apiKey(token)
                        .build()
        );
        UnleashProvider.initialize(unleash);
        return unleash;
    }
   
}
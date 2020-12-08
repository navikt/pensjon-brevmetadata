package no.nav.pensjonbrevdata.unleash;

import static java.lang.Long.parseLong;
import static java.lang.System.getProperty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.strategy.Strategy;
import no.finn.unleash.util.UnleashConfig;
import no.nav.pensjonbrevdata.unleash.strategies.ByEnvironmentStrategy;
import no.nav.pensjonbrevdata.unleash.strategies.IsNotProdStrategy;
import no.nav.pensjonbrevdata.unleash.strategies.IsTest;

@Configuration
public class UnleashEndpointConfig {

    @Value("${unleash.endpoint.url}")
    private String endpoint;
    
    @Value("${unleash.toggle.interval}")
    private String togglesInterval;
    
    
    
    
    @Bean
    public UnleashConfig unleashConfig() { 
    	
        return UnleashConfig.builder()
                .appName("pensjon-brevdata")
                .fetchTogglesInterval(parseLong(togglesInterval))
                .unleashAPI(endpoint)
                .build();
    }

    @Bean
    @Autowired
    public Unleash defaultUnleash(UnleashConfig unleashConfig) {
        Strategy[] strategies = {new IsNotProdStrategy(), new ByEnvironmentStrategy(), new IsTest()};
        DefaultUnleash unleash = new DefaultUnleash(unleashConfig, strategies);
        UnleashProvider.initialize(unleash);
        return unleash;
    }

   

   
}
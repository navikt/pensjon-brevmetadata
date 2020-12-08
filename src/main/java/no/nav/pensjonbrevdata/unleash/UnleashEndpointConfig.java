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
    
    @Value("#{environment.NAIS_NAMESPACE}")
    private String naisNamespace;

    @Value("#{environment.NAIS_APP_NAME}")
    private String naisAppName;
    
    
    private static String appname(String naisAppName) {
    	if (StringUtils.isEmpty(naisAppName)) return "pensjon-brevmetadata";
    	if (naisAppName.matches("^.*-[a-z][0-9]$")) {
    		return naisAppName.substring(0,naisAppName.lastIndexOf("-"));
    	}
    	
    	
    	return naisAppName;
    	 
    }
    
    private static String environment(String naisAppName,String naisNamespace) {
    	
    	if (StringUtils.isEmpty(naisNamespace)) return "local";
    	
    	if (naisAppName.lastIndexOf("-") > 0) {
    		String env = naisAppName.substring(naisAppName.lastIndexOf("-")+1, naisAppName.length() );
    		if  (env.matches("[a-z][0-9]")) {
    			return env;
    		}
    		
    	}
    	
    	return "undefined";
    }
    
    
    
    
    
    @Bean
    public UnleashConfig unleashConfig() {
        String envName = getProperty("environment.name");
        String environmentName = null != envName ? envName : "local";
     

        
        System.out.println("My environment name is : " + envName);
       
         
        
        return UnleashConfig.builder()
                .appName("pensjon-brevdata")
                .environment(environmentName)
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
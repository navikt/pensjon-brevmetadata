package no.nav.pensjonbrevdata.unleash.strategies;

import java.util.Map;

import no.finn.unleash.strategy.Strategy;
import org.apache.commons.lang3.StringUtils;

public class IsNotProdStrategy implements Strategy {
    @Override
    public String getName() {
        return "isNotProd";
    }

    @Override
    public boolean isEnabled(Map<String, String> map) {
        return !( StringUtils.startsWithIgnoreCase(System.getenv("NAIS_CLUSTER_NAME"), "prod-") ||
                new Environment().isCurrentEnvironment("prodlik") );
    }
}
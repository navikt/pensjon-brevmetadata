package no.nav.pensjonbrevdata.unleash.strategies;

import java.util.Map;

import no.finn.unleash.strategy.Strategy;

public class IsNotProdStrategy implements Strategy {
    @Override
    public String getName() {
        return "isNotProd";
    }

    @Override
    public boolean isEnabled(Map<String, String> map) {
        return !"p".equals(System.getProperty("environment.name", "local"));
    }
}
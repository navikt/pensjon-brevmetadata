package no.nav.pensjonbrevdata.unleash.strategies;

import static java.util.Optional.ofNullable;

import java.util.Arrays;
import java.util.Map;

import no.finn.unleash.strategy.Strategy;
import org.apache.commons.lang3.StringUtils;

public class ByEnvironmentStrategy implements Strategy {

	// dette settes via nais.yml
    private static final String ENVIRONMENT_PROPERTY = "ENVIRONMENT_NAME";

    @Override
    public String getName() {
        return "byEnvironment";
    }

    @Override
    public boolean isEnabled(Map<String, String> parameters) {
        return ofNullable(parameters)
                .map(par -> par.get("miljø"))
                .filter(s -> !s.isEmpty())
                .map(miljoer -> miljoer.split(","))
                .map(Arrays::stream)
                .map(miljoer -> miljoer.anyMatch(this::isCurrentEnvironment))
                .orElse(false);
    }

    private boolean isCurrentEnvironment(String env) {
        return StringUtils.equalsIgnoreCase(env, System.getenv().getOrDefault(ENVIRONMENT_PROPERTY,"local"));
    }
}
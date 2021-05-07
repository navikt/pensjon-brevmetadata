package no.nav.pensjonbrevdata.unleash.strategies;

import org.apache.commons.lang3.StringUtils;

public class Environment {
    private static final String ENVIRONMENT_NAME = "ENVIRONMENT_NAME";

    boolean isCurrentEnvironment(String env) {
        return StringUtils.equalsIgnoreCase(env, System.getenv().get(ENVIRONMENT_NAME));
    }
}

package no.nav.pensjonbrevdata.unleash.strategies;

import java.util.Map;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import no.finn.unleash.strategy.Strategy;

public class IsTest implements Strategy {
	
	private static boolean isTest= System.getenv().getOrDefault("NAIS_CLUSTER_NAME", "local").startsWith("dev-");

	@Override
	public String getName() {
		return "isTest";
	}

	@Override
	public boolean isEnabled(Map<String, String> parameters) {
		return isTest;
	}

	
	
	
}

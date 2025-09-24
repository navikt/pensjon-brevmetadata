package no.nav.pensjonbrevdata.unleash;

import io.getunleash.Unleash;

public class UnleashProvider {
    private static Unleash unleash;

    public static void initialize(Unleash unleash) {
        UnleashProvider.unleash = unleash;
    }

    public static Toggle toggle(String toggle) {
        return new Toggle(toggle);
    }

    static void shutdown() {
        unleash.shutdown();
    }

    public record Toggle(String toggle) {

        public boolean isDisabled() {
            return !unleash.isEnabled(toggle);
        }

        public boolean isEnabled() {
            return unleash.isEnabled(toggle);
        }
    }
}

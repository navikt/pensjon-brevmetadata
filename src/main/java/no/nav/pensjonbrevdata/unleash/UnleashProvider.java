package no.nav.pensjonbrevdata.unleash;

import io.getunleash.Unleash;

public class UnleashProvider {
    private static Unleash unleash;

    public static void initialize(Unleash unleash) {
        UnleashProvider.unleash = unleash;
    }

    public static Unleash get() {
        return unleash;
    }

    public static Toggle toggle(String toggle) {
        return new Toggle(toggle);
    }

    public static class Toggle {

        public final String toggle;

        Toggle(String toggle) {
            this.toggle = toggle;
        }

        public boolean isDisabled() {
            return !unleash.isEnabled(toggle);
        }

        public boolean isEnabled() {
            return unleash.isEnabled(toggle);
        }
    }
}

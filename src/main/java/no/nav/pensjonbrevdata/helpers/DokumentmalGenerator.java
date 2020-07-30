package no.nav.pensjonbrevdata.helpers;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class DokumentmalGenerator implements Function<String, String> {
    private String dokumentMalType;

    public DokumentmalGenerator(String dokumentMalType) {
        this.dokumentMalType = dokumentMalType;
    }

    @Override
    public String apply(String dokumentmalId) {
        try {
            return new XsdFileReader().read("xsd" + File.separator + dokumentMalType + File.separator + dokumentmalId + ".xsd");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

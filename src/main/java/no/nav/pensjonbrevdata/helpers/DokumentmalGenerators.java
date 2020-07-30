package no.nav.pensjonbrevdata.helpers;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class DokumentmalGenerators {

    public static Function<String, String> dokumentmalGenerator = dokumentmalBuilder("dokumentmal");
    public static Function<String, String> fellesmalGenerator = dokumentmalBuilder("felles");

    private DokumentmalGenerators() { }

    private static Function<String, String> dokumentmalBuilder(String dokumentMalType){
        return dokumentmalId -> {
            try {
                return new XsdFileReader().read("xsd" + File.separator + dokumentMalType + File.separator + dokumentmalId + ".xsd");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}

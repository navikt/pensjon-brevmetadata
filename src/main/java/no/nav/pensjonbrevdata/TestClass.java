package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevMapper;
import no.nav.pensjonbrevdata.mappers.DoksysVedleggMapper;
import no.nav.pensjonbrevdata.model.Doksysbrev;
import no.nav.pensjonbrevdata.model.SprakCode;

import java.util.Arrays;

//Temporary class
public class TestClass {
    public static void main(String[] args){
        BrevMapper brevMapper = new BrevMapper();
        DoksysVedleggMapper doksysVedleggMapper = new DoksysVedleggMapper();
        try {
            BrevdataEndpoint brevdataEndpoint = new BrevdataEndpoint();
            brevdataEndpoint.getBrevForBrevkode("TESTBREV");
            Doksysbrev doksysbrev = new Doksysbrev(
                    true,
                    "Et brev for å test nye muligheter",
                    "U",
                    "Ukjent",
                    "001221",
                    "00001",
                    doksysVedleggMapper.map("V0002", "V0004"),
                    Arrays.asList(SprakCode.NN, SprakCode.NO, SprakCode.EN));
            doksysbrev.generateDokumentmalFromFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

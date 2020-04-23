package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.mappers.BrevMapper;
import no.nav.pensjonbrevdata.model.Brev;

//Temporary class
public class TestClass {
    public static void main(String[] args){
        BrevMapper brevMapper = new BrevMapper();
        try {
            Brev brev = brevMapper.map("AP_COOL_BREV_AUTO");
            Brev brev2 = brevMapper.map("AP_COOL_BREV_MAN");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

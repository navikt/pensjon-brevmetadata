package no.nav.pensjonbrevdata;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import no.nav.pensjonbrevdata.model.Brev;
import no.nav.pensjonbrevdata.model.SprakCode;

@RestController
@RequestMapping("api/brevdata")
public class BrevdataEndpoint {
    private BrevdataProvider provider = new BrevdataProvider();

    @GetMapping("/sprakForBrevkode")
    public List<SprakCode> getSprakForBrevkode(@RequestParam(value = "brevkode") String brevkode){
        try {
            return provider.getSprakForBrevkode(brevkode);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        }
    }

    @GetMapping("/brevForBrevkode")
    public Brev getBrevForBrevkode(@RequestParam(value = "brevkode") String brevkode){
        try {
            return provider.getBrevForBrevkode(brevkode);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        }
    }

    @GetMapping("brevdataForSaktype")
    public List<Brev> getBrevdatForSaktype(@RequestParam(value = "saktype") String saktype){
        try {
            return provider.getBrevdataForSaktype(saktype);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        }
    }
    @GetMapping("brevkoderForSaktype")
    public List<String> getBrevkoderForSaktype(@RequestParam(value = "saktype") String saktype){
        try {
            return provider.getBrevkoderForSaktype(saktype);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        }
    }
}

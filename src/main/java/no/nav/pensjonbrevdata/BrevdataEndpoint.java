package no.nav.pensjonbrevdata;

import no.nav.pensjonbrevdata.model.Brev;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/brevdata")
public class BrevdataEndpoint {
    private BrevdataProvider provider = new BrevdataProvider();

    @GetMapping("/getSprakForBrevkode")
    public List<String> getSprakForBrevkode(@RequestParam(value = "brevkode") String brevkode){
        try {
            return provider.getSprakForBrevkode(brevkode);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        }
    }

    @GetMapping("/getBrevForBrevkode")
    public Brev getBrevForBrevkode(@RequestParam(value = "brevkode") String brevkode){
        try {
            return provider.getBrevForBrevkode(brevkode);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),e);
        }
    }
}

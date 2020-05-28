package no.nav.pensjonbrevdata;

import java.util.List;

import no.nav.pensjonbrevdata.model.Brevdata;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import no.nav.pensjonbrevdata.model.codes.SprakCode;

@RestController
@RequestMapping("api/brevdata")
public class BrevdataEndpoint {
    private final BrevdataProvider provider = new BrevdataProvider();

    @GetMapping("/sprakForBrevkode/{brevkode}")
    public List<SprakCode> getSprakForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getSprakForBrevkode(brevkode);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/brevForBrevkode/{brevkode}")
    public Brevdata getBrevForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getBrevForBrevkode(brevkode);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevdataForSaktype/{saktype}")
    public List<Brevdata> getBrevdatForSaktype(@PathVariable(value = "saktype") String saktype, @RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            return provider.getBrevdataForSaktype(saktype, includeXsd);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevkoderForSaktype/{saktype}")
    public List<String> getBrevkoderForSaktype(@PathVariable(value = "saktype") String saktype) {
        try {
            return provider.getBrevkoderForSaktype(saktype);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/allBrev")
    public List<Brevdata> getAllBrev(@RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            return provider.getAllBrev(includeXsd);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevKeyForBrevkodeIBrevsystem/{brevkodeIBrevsystem}")
    public List<String> getBrevKeyForBrevkodeIBrevsystem(@PathVariable(value = "brevkodeIBrevsystem") String brevkodeIBrevsystem) {
        try {
            return provider.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem);
        } catch (Exception e) {
            //TODO: Logging her
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}

package no.nav.pensjonbrevdata;

import static no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.dokumentmalGenerator;
import static no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.fellesmalGenerator;

import java.util.List;
import java.util.stream.Collectors;

import no.nav.pensjonbrevdata.model.Brevdata;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import no.nav.pensjonbrevdata.model.codes.SprakCode;

@RestController
@DependsOn({"defaultUnleash"})
@RequestMapping("api/brevdata")
public class BrevdataEndpoint {
    private BrevdataProvider provider;

    public BrevdataEndpoint(){
        provider = new BrevdataProvider();
    }
    //TODO: Implementere noen form for logging?

    @GetMapping("/sprakForBrevkode/{brevkode}")
    public List<SprakCode> getSprakForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getSprakForBrevkode(brevkode);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/brevForBrevkode/{brevkode}")
    public Brevdata getBrevForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getBrevForBrevkode(brevkode).medXSD(dokumentmalGenerator, fellesmalGenerator);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevdataForSaktype/{saktype}")
    public List<Brevdata> getBrevdataForSaktype(@PathVariable(value = "saktype") String saktype, @RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            List<Brevdata> brevdata = provider.getBrevdataForSaktype(saktype);
            return includeXsd ? brevdata.stream().map((brev) -> brev.medXSD(dokumentmalGenerator, fellesmalGenerator)).collect(Collectors.toList()) :
                    brevdata;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevkoderForSaktype/{saktype}")
    public List<String> getBrevkoderForSaktype(@PathVariable(value = "saktype") String saktype) {
        try {
            return provider.getBrevkoderForSaktype(saktype);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/allBrev")
    public List<Brevdata> getAllBrev(@RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            List<Brevdata> brevdataList = provider.getAllBrev();
            return includeXsd ? brevdataList.stream().map((brevdata -> brevdata.medXSD(dokumentmalGenerator, fellesmalGenerator))).collect(Collectors.toList()) :
                    brevdataList;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevKeyForBrevkodeIBrevsystem/{brevkodeIBrevsystem}")
    public List<String> getBrevKeyForBrevkodeIBrevsystem(@PathVariable(value = "brevkodeIBrevsystem") String brevkodeIBrevsystem) {
        try {
            return provider.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public void setProvider(BrevdataProvider provider) {
        this.provider = provider;
    }
}

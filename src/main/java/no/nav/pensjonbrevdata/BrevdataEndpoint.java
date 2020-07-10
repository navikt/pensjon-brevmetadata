package no.nav.pensjonbrevdata;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import no.nav.pensjonbrevdata.json.CompleteJSONVisitor;
import no.nav.pensjonbrevdata.json.JSONList;
import no.nav.pensjonbrevdata.model.codes.SprakCode;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public String getSprakForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            List<SprakCode> sprak = provider.getSprakForBrevkode(brevkode);
            return sprak==null?"":JSONList.jsonIfiableList(sprak).asJSON();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/brevForBrevkode/{brevkode}")
    public String getBrevForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getBrevForBrevkode(brevkode).visit(new CompleteJSONVisitor()).asJSON();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed when trying to read xsd-file for brev", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevdataForSaktype/{saktype}")
    public String getBrevdataForSaktype(@PathVariable(value = "saktype") String saktype, @RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            return JSONList.jsonVisitableList(provider.getBrevdataForSaktype(saktype, includeXsd)).asJSON();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed when trying to read xsd-file for brev", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevkoderForSaktype/{saktype}")
    public String getBrevkoderForSaktype(@PathVariable(value = "saktype") String saktype) {
        try {
            return asJSONArray(provider.getBrevkoderForSaktype(saktype));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/allBrev")
    public String getAllBrev(@RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            return JSONList.jsonVisitableList(provider.getAllBrev(includeXsd)).asJSON();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed when trying to read xsd-file for brev", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevKeyForBrevkodeIBrevsystem/{brevkodeIBrevsystem}")
    public String getBrevKeyForBrevkodeIBrevsystem(@PathVariable(value = "brevkodeIBrevsystem") String brevkodeIBrevsystem) {
        try {
            return asJSONArray(provider.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    private static String asJSONArray(List<String> list) {
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        list.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    public void setProvider(BrevdataProvider provider) {
        this.provider = provider;
    }
}

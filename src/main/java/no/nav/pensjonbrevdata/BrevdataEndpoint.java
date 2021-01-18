package no.nav.pensjonbrevdata;

import static no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.dokumentmalGenerator;
import static no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.fellesmalGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import no.nav.pensjonbrevdata.dto.BrevdataDTO;
import no.nav.pensjonbrevdata.helpers.BrevMetaData;
import no.nav.pensjonbrevdata.model.Brevdata;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BrevdataEndpoint.class);

    private BrevdataProvider provider;

    public BrevdataEndpoint(){
        provider = new BrevdataProvider();
    }

    @GetMapping("/sprakForBrevkode/{brevkode}")
    public List<SprakCode> getSprakForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getSprakForBrevkode(brevkode);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Faulty request when calling sprakForBrevkode.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            LOGGER.error("Feil ved kall til sprakForBrevkode: " + e.getMessage() + " for kode: " + brevkode, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/brevForBrevkode/{brevkode}")
    public BrevdataDTO getBrevForBrevkode(@PathVariable(value = "brevkode") String brevkode) {
        try {
            return provider.getBrevForBrevkode(brevkode).medXSD(dokumentmalGenerator, fellesmalGenerator).toDTO();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Faulty request when calling brevForBrevkode.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            LOGGER.error("Feil ved kall til brevForBrevkode: " + e.getMessage() + " for kode: " + brevkode, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevdataForSaktype/{saktype}")
    public List<BrevdataDTO> getBrevdataForSaktype(@PathVariable(value = "saktype") String saktype, @RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            List<Brevdata> brevdata = provider.getBrevdataForSaktype(saktype);
            return (includeXsd ? brevdata.stream().map((brev) -> brev.medXSD(dokumentmalGenerator, fellesmalGenerator)).collect(Collectors.toList()) :
                    brevdata).stream().map(Brevdata::toDTO).collect(Collectors.toUnmodifiableList());
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Faulty request when calling brevdataForSaktype.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            LOGGER.error("Feil ved kall til brevdataForSaktype: " + e.getMessage() + " for saktype: " + saktype, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevkoderForSaktype/{saktype}")
    public List<String> getBrevkoderForSaktype(@PathVariable(value = "saktype") String saktype) {
        try {
            return provider.getBrevkoderForSaktype(saktype);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Faulty request when calling brevkoderForSaktype.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            LOGGER.error("Feil ved kall til brevkoderForSaktype: " + e.getMessage() + " for saktype: " + saktype, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/allBrev")
    public List<BrevdataDTO> getAllBrev(@RequestParam(value = "includeXsd") boolean includeXsd) {
        try {
            List<Brevdata> brevdataList = provider.getAllBrev();
            return (includeXsd ? brevdataList.stream().map((brevdata -> brevdata.medXSD(dokumentmalGenerator, fellesmalGenerator))).collect(Collectors.toList()) :
                    brevdataList).stream().map(Brevdata::toDTO).collect(Collectors.toUnmodifiableList());
        } catch (RuntimeException e) {
            LOGGER.error("Feil ved kall til allBrev: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/brevForCodes")
    public List<BrevdataDTO> getBrevForCodes(@RequestParam(value = "brevKoder") List<String> brevKoder, @RequestParam(value = "includeXsd") boolean includeXsd) {
        return brevKoder.stream()
                .filter(StringUtils::isNotBlank)
                .map(code -> provider.getBrevForBrevkode(code.trim()))
                .map(brev -> includeXsd ? brev.medXSD(dokumentmalGenerator, fellesmalGenerator) : brev)
                .map(Brevdata::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/batchbBrevMapping")
    public List<Map<String,String>> getBatchBrevMapping(@RequestParam(value = "batchBrevKoder") List<String> brevKoder) {
        return brevKoder.stream()
                .filter(code -> StringUtils.isNotBlank(code))
                .map(code -> {
                    Brevdata brev = provider.getBrevForBrevkode(code.trim());
                    Map<String, String> map = new HashMap<>();
                    map.put("batch", code);
                    map.put("brev", brev.getBrevkodeIBrevsystem());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/brevKeyForBrevkodeIBrevsystem/{brevkodeIBrevsystem}")
    public List<String> getBrevKeyForBrevkodeIBrevsystem(@PathVariable(value = "brevkodeIBrevsystem") String brevkodeIBrevsystem) {
        try {
            return provider.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem);
        } catch (RuntimeException e) {
            LOGGER.error("Feil ved kall til allBrev: " + e.getMessage() + " for brevkodeIBrevsystem " + brevkodeIBrevsystem, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("brevTypeCode")
    public Set<String> getBrevTypeCode() {
        return BrevMetaData.getBrevTypeCodes();
    }

    public void setProvider(BrevdataProvider provider) {
        this.provider = provider;
    }
}

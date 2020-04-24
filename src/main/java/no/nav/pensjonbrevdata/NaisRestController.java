package no.nav.pensjonbrevdata;

import static no.nav.pensjonbrevdata.config.BrevdataFeature.BREVDATA_ENABLE_UNLEASH;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/internal")
public class NaisRestController {

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity testunleash() {

        if (toggle(BREVDATA_ENABLE_UNLEASH).isEnabled()) {
            return new ResponseEntity<>("Unleash is Enabled", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unleash is Disabled", HttpStatus.OK);
    }

    @RequestMapping(path = "isAlive", method = RequestMethod.GET)
    public ResponseEntity isAlive() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "isReady", method = RequestMethod.GET)
    public ResponseEntity isReady() {
        return new ResponseEntity(HttpStatus.OK);
    }
}

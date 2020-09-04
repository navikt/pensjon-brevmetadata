package no.nav.pensjonbrevdata;

import static no.nav.pensjonbrevdata.config.BrevdataFeature.BREVDATA_ENABLE_UNLEASH;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

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

    @RequestMapping(path = "prometheus", method = RequestMethod.GET, produces = TextFormat.CONTENT_TYPE_004)
    public ResponseEntity prometheus() throws IOException {
        Writer writer = new StringWriter();
        try {
            TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
        } finally {
            writer.close();
        }

        return ResponseEntity.ok().body(writer.toString());
    }

    @RequestMapping(path = "isAlive", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void isAlive() {
    }

    @RequestMapping(path = "isReady", method = RequestMethod.GET)
    public ResponseEntity isReady() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

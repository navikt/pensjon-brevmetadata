package no.nav.pensjonbrevdata;

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

    @RequestMapping(path = "prometheus", method = RequestMethod.GET, produces = TextFormat.CONTENT_TYPE_004)
    public ResponseEntity prometheus() throws IOException {
        Writer writer = new StringWriter();
        try (writer) {
            TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
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

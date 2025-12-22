package no.nav.pensjonbrevdata

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.io.StringWriter
import java.io.Writer

@RestController
@RequestMapping("api/internal")
class NaisRestController {
    @RequestMapping(path = ["prometheus"], method = [RequestMethod.GET], produces = [TextFormat.CONTENT_TYPE_004])
    fun prometheus(): ResponseEntity<*> {
        val writer: Writer = StringWriter()
        writer.use {
            TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples())
        }
        return ResponseEntity.ok().body(writer.toString())
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = ["isAlive"], method = [RequestMethod.GET])
    fun isAlive() {}

    @RequestMapping(path = ["isReady"], method = [RequestMethod.GET])
    fun isReady() = ResponseEntity<Any>(HttpStatus.NO_CONTENT)
}

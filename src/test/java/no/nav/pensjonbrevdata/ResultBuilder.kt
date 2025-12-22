package no.nav.pensjonbrevdata

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.json.JsonMapper
import no.nav.pensjonbrevdata.TestDataHolder.brevkoder
import no.nav.pensjonbrevdata.TestDataHolder.brevkoderIBrevSystem
import no.nav.pensjonbrevdata.TestDataHolder.sakstyper
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper
import java.nio.file.Files
import java.nio.file.Path

/**
 * Bygger en base-line av responser som applikasjonen gjør akkurat nå, og som benyttes av KomponentTest
 */
private val be = BrevdataEndpoint(BrevdataProvider(BrevdataMapper(), SakBrevMapper()))
private val objectMapper: ObjectWriter = JsonMapper.builder()
    .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
    .build()
    .writer(
        DefaultPrettyPrinter()
            .withObjectIndenter(DefaultIndenter().withLinefeed("\n"))
    )

fun writeString(brevkode: String, dir: String?, toJSON: Any?) {
    val path = Path.of("src", "test", "resources", dir)
    Files.createDirectories(path)
    Files.writeString(path.resolve(brevkode), toJSON(toJSON))
}

fun toJSON(obj: Any?) = obj?.let { objectMapper.writeValueAsString(it) } ?: ""

fun main() {
    for (brevkode in brevkoder()) {
        writeString(brevkode, "brevForBrevkode", be.getBrevForBrevkode(brevkode))
        writeString(brevkode, "sprakForBrevkode", be.getSprakForBrevkode(brevkode))
    }
    for (sakstype in sakstyper()) {
        writeString(sakstype, "brevdataForSaktype/" + true, be.getBrevdataForSaktype(sakstype, true))
        writeString(sakstype, "brevdataForSaktype/" + false, be.getBrevdataForSaktype(sakstype, false))
        writeString(sakstype, "brevkoderForSaktype", be.getBrevkoderForSaktype(sakstype))
    }
    for (brevkodeIBrevsystem in brevkoderIBrevSystem()) {
        writeString(
            brevkodeIBrevsystem,
            "brevKeyForBrevkodeIBrevsystem",
            be.getBrevKeyForBrevkodeIBrevsystem(brevkodeIBrevsystem)
        )
    }

    writeString("false", "allBrev", be.getAllBrev(false))
    writeString("true", "allBrev", be.getAllBrev(true))
}
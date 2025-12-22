package no.nav.pensjonbrevdata

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.helpers.BrevMetaData.brevTypeCodes
import no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.dokumentmalGenerator
import no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.fellesmalGenerator
import no.nav.pensjonbrevdata.model.Brevdata
import no.nav.pensjonbrevdata.model.codes.SprakCode
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.function.Function
import java.util.stream.Collectors
import kotlin.collections.map

@RestController
@RequestMapping("api/brevdata")
class BrevdataEndpoint @Autowired constructor(private val provider: BrevdataProvider) {
    @GetMapping("/sprakForBrevkode/{brevkode}")
    fun getSprakForBrevkode(@PathVariable brevkode: String): List<SprakCode>? {
        try {
            return provider.getSprakForBrevkode(brevkode)
        } catch (e: IllegalArgumentException) {
            LOGGER.warn("Faulty request when calling sprakForBrevkode.", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        } catch (e: RuntimeException) {
            LOGGER.error("Feil ved kall til sprakForBrevkode: " + e.message + " for kode: " + brevkode, e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message, e)
        }
    }

    @GetMapping(value = ["/brevForBrevkode/{brevkode}"])
    fun getBrevForBrevkode(@PathVariable brevkode: String): BrevdataDTO? {
        try {
            return provider.getBrevForBrevkode(brevkode)!!.medXSD(dokumentmalGenerator, fellesmalGenerator).toDTO()
        } catch (e: IllegalArgumentException) {
            LOGGER.warn("Faulty request when calling brevForBrevkode.", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        } catch (e: RuntimeException) {
            LOGGER.error("Feil ved kall til brevForBrevkode: " + e.message + " for kode: " + brevkode, e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message, e)
        }
    }

    @GetMapping("/brevdataForSaktype/{saktype}")
    fun getBrevdataForSaktype(
        @PathVariable saktype: String,
        @RequestParam(value = "includeXsd") includeXsd: Boolean
    ): List<BrevdataDTO> {
        try {
            val brevdata = provider.getBrevdataForSaktype(saktype)
            return (if (includeXsd) {
                brevdata.map { it!!.medXSD(dokumentmalGenerator, fellesmalGenerator) }
            } else {
                brevdata
            })
                .map { obj: Brevdata? -> obj!!.toDTO() }
        } catch (e: IllegalArgumentException) {
            LOGGER.warn("Faulty request when calling brevdataForSaktype.", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        } catch (e: RuntimeException) {
            LOGGER.error("Feil ved kall til brevdataForSaktype: " + e.message + " for saktype: " + saktype, e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message, e)
        }
    }

    @GetMapping("/brevkoderForSaktype/{saktype}")
    fun getBrevkoderForSaktype(@PathVariable saktype: String): List<String> {
        try {
            return provider.getBrevkoderForSaktype(saktype)
        } catch (e: IllegalArgumentException) {
            LOGGER.warn("Faulty request when calling brevkoderForSaktype.", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        } catch (e: RuntimeException) {
            LOGGER.error("Feil ved kall til brevkoderForSaktype: " + e.message + " for saktype: " + saktype, e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message, e)
        }
    }

    @GetMapping("/allBrev")
    fun getAllBrev(
        @RequestParam(
            value = "includeXsd",
            required = false
        ) includeXsd: Boolean
    ): List<BrevdataDTO> {
        try {
            val brevdataList: List<Brevdata> = provider.allBrev
            return (if (includeXsd) {
                brevdataList
                    .stream()
                    .map<Brevdata?>((Function { brevdata: Brevdata? ->
                    brevdata!!.medXSD(
                        dokumentmalGenerator,
                        fellesmalGenerator
                    )
                }))
                    .collect(Collectors.toUnmodifiableList())
            } else {
                brevdataList
            })
                .map { obj: Brevdata? -> obj!!.toDTO() }
        } catch (e: RuntimeException) {
            LOGGER.error("Feil ved kall til allBrev: " + e.message, e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message, e)
        }
    }

    @GetMapping("/brevForCodes")
    fun getBrevForCodes(
        @RequestParam(value = "brevKoder") brevKoder: List<String?>,
        @RequestParam(value = "includeXsd") includeXsd: Boolean
    ): List<BrevdataDTO> {
        return brevKoder
            .filter { cs: String? -> StringUtils.isNotBlank(cs) }
            .map { code: String? -> provider.getBrevForBrevkode(code!!.trim { it <= ' ' }) }
            .map { brev: Brevdata? ->
                if (includeXsd) brev!!.medXSD(
                    dokumentmalGenerator,
                    fellesmalGenerator
                ) else brev
            }
            .map { obj: Brevdata? -> obj!!.toDTO() }
    }

    @GetMapping("/batchbBrevMapping")
    fun getBatchBrevMapping(@RequestParam(value = "batchBrevKoder") brevKoder: List<String>): List<Map<String, String>> {
        return brevKoder
            .filter { StringUtils.isNotBlank(it) }
            .map { code: String ->
                mapOf(
                    "batch" to code,
                    "brev" to provider.getBrevForBrevkode(code.trim())!!.brevkodeIBrevsystem!!
                )
            }
    }

    @GetMapping("/brevKeyForBrevkodeIBrevsystem/{brevkodeIBrevsystem}")
    fun getBrevKeyForBrevkodeIBrevsystem(@PathVariable brevkodeIBrevsystem: String): List<String> {
        try {
            return provider.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem)
        } catch (e: RuntimeException) {
            LOGGER.error(
                "Feil ved kall til allBrev: " + e.message + " for brevkodeIBrevsystem " + brevkodeIBrevsystem,
                e
            )
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message, e)
        }
    }

    @get:GetMapping("/eblanketter")
    val eblanketter: List<BrevdataDTO?>
        get() = provider.eblanketter.stream().map<BrevdataDTO?> { obj: Brevdata? -> obj!!.toDTO() }.toList()

    @get:GetMapping("brevTypeCode")
    val brevTypeCode: Set<String>
        get() = brevTypeCodes

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BrevdataEndpoint::class.java)
    }
}

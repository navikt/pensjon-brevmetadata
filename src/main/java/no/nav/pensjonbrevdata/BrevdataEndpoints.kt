package no.nav.pensjonbrevdata

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail
import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.dokumentmalGenerator
import no.nav.pensjonbrevdata.helpers.DokumentmalGenerators.fellesmalGenerator
import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapperImpl
import no.nav.pensjonbrevdata.model.Brevdata
import kotlin.collections.map


fun Routing.routes(provider: BrevdataProvider) {
    route("/api/internal") {
        healthRoute()
    }
    route("api/brevdata") {
        get("/sprakForBrevkode/{brevkode}") {
            val brevkode = call.parameters.getOrFail("brevkode")
            val sprakForBrevkode = provider.getSprakForBrevkode(brevkode)
            if (sprakForBrevkode != null) {
                call.respond(sprakForBrevkode)
            } else {
                call.respond(HttpStatusCode.OK)
            }
        }
        get("/brevForBrevkode/{brevkode}") {
            val brevkode = call.parameters.getOrFail("brevkode")
            val message: BrevdataDTO = provider.getBrevForBrevkode(brevkode).medXSD(dokumentmalGenerator, fellesmalGenerator).toDTO()
            call.respond(message)
        }
        get("/brevdataForSaktype/{saktype}") {
            val saktype = call.parameters.getOrFail("saktype")
            val includeXsd = call.request.queryParameters["includeXsd"]?.toBoolean() ?: false
            val brevdata = provider.getBrevdataForSaktype(saktype)
            val dto = (if (includeXsd) {
                brevdata.map { it.medXSD(dokumentmalGenerator, fellesmalGenerator) }
            } else {
                brevdata
            }).map { it.toDTO() }
            call.respond(dto)
        }
        get("/brevkoderForSaktype/{saktype}") {
            val saktype = call.parameters.getOrFail("saktype")
            call.respond(provider.getBrevkoderForSaktype(saktype))
        }
        get("/allBrev") {
            val includeXsd = call.request.queryParameters["includeXsd"]?.toBoolean() ?: false
            val brevdataList: List<Brevdata> = provider.allBrev
            val dto = (if (includeXsd) {
                brevdataList.map { it.medXSD(dokumentmalGenerator, fellesmalGenerator) }
            } else {
                brevdataList
            }).map { it.toDTO() }
            call.respond(dto)
        }
        get("/brevForCodes") {
            val brevKoder: List<String> =
                (call.request.queryParameters["brevkoder"]?.split(",") ?: listOf())
            val includeXsd = call.request.queryParameters["includeXsd"]?.toBoolean() ?: false
            val dto = brevKoder.filter { it.isNotBlank() }
                .map { code -> provider.getBrevForBrevkode(code.trim()) }
                .map { brev ->
                    if (includeXsd) brev.medXSD(
                        dokumentmalGenerator,
                        fellesmalGenerator
                    ) else brev
                }
                .map { it.toDTO() }
            call.respond(dto)
        }
        get("/batchbBrevMapping") {
            val brevKoder: List<String> =
                (call.request.queryParameters["brevkoder"]?.split(",") ?: listOf())
            call.respond(
                brevKoder
                    .filter { it.isNotBlank() }
                    .map { code: String ->
                        mapOf(
                            "batch" to code,
                            "brev" to provider.getBrevForBrevkode(code.trim()).brevkodeIBrevsystem
                        )
                    })
        }
        get("/brevKeyForBrevkodeIBrevsystem/{brevkodeIBrevsystem}") {
            val brevkodeIBrevsystem = call.parameters.getOrFail("brevkodeIBrevsystem")
            call.respond(provider.getBrevKeysForBrevkodeIBrevsystem(brevkodeIBrevsystem))
        }
        get("/eblanketter") {
            call.respond(provider.eblanketter.map { it.toDTO() })
        }
        get("brevTypeCode") {
            call.respond(BrevdataMapperImpl().allBrevAsList.map { it.brevkodeIBrevsystem }.toSet())
        }
    }
}
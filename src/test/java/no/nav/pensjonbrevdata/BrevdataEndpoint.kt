package no.nav.pensjonbrevdata

import no.nav.pensjonbrevdata.dto.BrevdataDTO
import no.nav.pensjonbrevdata.helpers.DokumentmalGenerators
import no.nav.pensjonbrevdata.model.Brevdata

class BrevdataEndpoint(private val provider: BrevdataProvider) {
    fun getBrevdataForSaktype(
        saktype: String, includeXsd: Boolean,
    ): List<BrevdataDTO?> {
        val brevdata = provider.getBrevdataForSaktype(saktype)
        return (if (includeXsd) {
            brevdata.map {
                it?.medXSD(
                    DokumentmalGenerators.dokumentmalGenerator,
                    DokumentmalGenerators.fellesmalGenerator
                )
            }
        } else {
            brevdata
        })
            .map { it?.toDTO() }
    }


    fun getAllBrev(includeXsd: Boolean): List<BrevdataDTO> {
        val brevdataList: List<Brevdata> = provider.allBrev
        return (if (includeXsd) {
            brevdataList.map {
                it.medXSD(
                    DokumentmalGenerators.dokumentmalGenerator,
                    DokumentmalGenerators.fellesmalGenerator
                )
            }
        } else {
            brevdataList
        })
            .map { it.toDTO() }
    }
}
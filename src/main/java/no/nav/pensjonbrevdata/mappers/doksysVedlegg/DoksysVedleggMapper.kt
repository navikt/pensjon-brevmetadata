package no.nav.pensjonbrevdata.mappers.doksysVedlegg

import no.nav.pensjonbrevdata.model.DoksysVedlegg
import java.util.Arrays
import java.util.Map
import java.util.function.Supplier

class DoksysVedleggMapper {
    private val vedleggMap: MutableMap<String?, DoksysVedlegg?> = Map.ofEntries<String?, DoksysVedlegg?>(
        Map.entry<String?, DoksysVedlegg?>(
            "AP2025_OPPL_BER_V1", DoksysVedlegg(
                "AP2025_OPPL_BER_V1",
                "VEDLEGG: Opplysninger brukt i beregningen. AP2025",
                "V00011",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "RETTIGH_V1", DoksysVedlegg(
                "RETTIGH_V1",
                "VEDLEGG: Rettigheter. Versjon 1",
                "V00001",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "RETTIGH_PLIKT_V1", DoksysVedlegg(
                "RETTIGH_PLIKT_V1",
                "VEDLEGG: Orientering om rettigheter og plikter. Versjon 1",
                "V00002",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AP_MND_UTB_V1", DoksysVedlegg(
                "AP_MND_UTB_V1",
                "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1	",
                "V00003",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AP_MND_UTB_AP2025", DoksysVedlegg(
                "AP_MND_UTB_AP2025",
                "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1	",
                "V00010",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AP_OPPL_BER_V1", DoksysVedlegg(
                "AP_OPPL_BER_V1",
                "VEDLEGG: Opplysninger brukt i beregningen. Versjon 1",
                "V00004",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AP_OPPL_BER_END_V1", DoksysVedlegg(
                "AP_OPPL_BER_END_V1",
                "VEDLEGG: Opplysninger brukt i beregningen endret uttaksgrad. Versjon 1",
                "V00005",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AP_AVDOD_OPPL_BER_V1", DoksysVedlegg(
                "AP_AVDOD_OPPL_BER_V1",
                "VEDLEGG: Opplysninger om avdøde brukt i beregningen.",
                "V00006",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AP_MND_UTB_V4", DoksysVedlegg(
                "AP_MND_UTB_V4",
                "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 4",
                "V00007",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "AFP_PRIV_MND_UTB_V1", DoksysVedlegg(
                "AFP_PRIV_MND_UTB_V1",
                "VEDLEGG: Dette er din månedlige pensjon før skatt. Versjon 1",
                "V00008",
                "00001"
            )
        ),
        Map.entry<String?, DoksysVedlegg?>(
            "INFO_MEDLEM_HELSE_V1", DoksysVedlegg(
                "INFO_MEDLEM_HELSE_V1",
                "VEDLEGG: Informasjon om medlemskap i folketrygden og rett til helsetjenester",
                "V00009",
                "00001"
            )
        )
    )

    fun map(vararg vedleggCodes: String?): Supplier<MutableList<DoksysVedlegg?>?> {
        return Supplier {
            Arrays.stream<String?>(vedleggCodes).map<DoksysVedlegg?> { key: String? -> vedleggMap.get(key) }.toList()
        }
    }
}

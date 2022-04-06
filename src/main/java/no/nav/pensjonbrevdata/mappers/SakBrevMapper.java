package no.nav.pensjonbrevdata.mappers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import no.nav.pensjonbrevdata.unleash.UnleashProvider;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static no.nav.pensjonbrevdata.config.BrevdataFeature.*;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

import java.util.*;
import java.util.stream.Collectors;

public class SakBrevMapper {
    private final Map<String, List<String>> sakToBrevMap;

    // Brevkoder som skal legges til, men kun når feature er aktivert. På denne måten kan en kode legges til i dev og testes,
    // og deretter legges til i produksjon. Man kan fjerne et innslag her når det er testet og fungerer i produksjon.
    // Nøkkel skal være brevkode og verdi skal være feature-toggle.
    public static final Map<String, UnleashProvider.Toggle> addedBrevkoder = ImmutableMap.of(
            "VEDTAK_TILBAKEKREV", toggle(BRUK_VEDTAK_TILBAKEKREV),
            "AFP_INNV_MAN", toggle(BRUK_AFP_INNV_MAN)
    );

    // Brevkoder som skal fjernes, men kun når feature er aktivert. På denne måten kan en kode fjernes i dev og testes,
    // og deretter fjernes i produksjon. Man kan fjerne et innslag her når det er testet og fungerer i produksjon.
    public static final List<BrevkodeToRemove> removedBrevkoder = asList(
            new BrevkodeToRemove("PE_FT_01_001", toggle(FJERNE_BREV_PL_4961), asList("AFP", "UFOREP", "GENRL", "ALDER"))
    );

    public SakBrevMapper() {
        sakToBrevMap = new HashMap<>();

        sakToBrevMap.put("FAM_PL", asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "VARSEL_REVURD", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("GAM_YRK", asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN"));
        sakToBrevMap.put("OMSORG", asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_001", "PE_IY_04_010", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_101", "PE_IY_05_102", "PE_IY_05_104", "PE_IY_05_105", "PE_IY_05_200", "PE_IY_05_201", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "OMSORG_EGEN_MAN", "AP_INFO_STID_MAN", "HENT_INFO_MAN"));
        sakToBrevMap.put("AFP", asList("PE_AF_03_101", "PE_AF_04_001", "PE_AF_04_010", "PE_AF_04_020", "PE_AF_04_103", "PE_AF_04_104", "PE_AF_04_105", "PE_AF_04_106", "PE_AF_04_107", "PE_AF_04_108", "PE_AF_04_109", "PE_AF_04_110", "PE_AP_01_001", "PE_BA_01_108", "PE_FT_01_002", "PE_FT_01_003", "PE_FT_01_006", "PE_FT_01_007", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "VARSEL_REVURD", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET",  "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("BARNEP", asList("PE_AP_01_001", "PE_BP_01_001", "PE_BP_01_002", "PE_BP_04_001", "PE_BP_04_002", "PE_BP_04_010", "PE_BP_04_011", "PE_BP_04_020", "PE_BP_04_021", "PE_BP_04_030", "PE_BP_04_031", "PE_GP_01_010", "PE_GP_01_011", "PE_GP_01_012", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_165", "PE_IY_03_166", "PE_IY_03_167", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "VARSEL_REVURD", "DOD_INFO_RETT_MAN", "INFO_P1", "BP_AVSL_MAN", "BP_OPPH_MAN", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("UFOREP", asList("PE_AP_01_001", "PE_BA_01_108", "PE_FT_01_002", "PE_FT_01_003", "PE_FT_01_006", "PE_FT_01_007", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_169", "PE_IY_03_170", "PE_IY_03_171", "PE_IY_03_172", "PE_IY_03_173", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UP_01_001", "PE_UP_04_001", "PE_UP_04_010", "PE_UP_04_020", "PE_UP_04_021", "PE_UP_04_022", "PE_UP_04_023", "PE_UP_04_024", "PE_UP_04_025", "PE_UP_04_026", "PE_UP_04_027", "PE_UP_04_028", "PE_UP_04_029", "PE_UP_04_030", "PE_UP_07_010", "PE_UP_07_105", "PE_UP_07_100", "PE_UT_04_001", "PE_UT_04_002", "PE_UT_04_003", "PE_IY_03_167", "PE_UT_04_100", "PE_UT_04_101", "PE_UT_04_102", "PE_UT_04_103", "PE_UT_04_104", "PE_UT_04_114", "PE_UT_04_115", "PE_UT_04_117", "PE_UT_04_300", "PE_UT_04_004", "PE_UT_07_100", "PE_UT_04_401", "PE_UT_04_118", "PE_UT_04_400", "PE_UT_04_402", "PE_UT_04_106", "PE_UT_04_107", "PE_UT_04_108", "PE_UT_04_109", "PE_UT_06_300", "DOD_INFO_RETT_MAN", "INFO_P1", "AP_INFO_STID_MAN", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("GJENLEV", asList("PE_AP_01_001", "PE_GP_01_010", "PE_GP_01_011", "PE_GP_04_001", "PE_GP_04_010", "PE_GP_04_020", "PE_GP_04_022", "PE_GP_04_023", "PE_GP_04_024", "PE_GP_04_025", "PE_GP_04_026", "PE_GP_04_027", "PE_GP_04_028", "PE_GP_04_029", "PE_GP_04_030", "PE_GP_04_031", "PE_GP_04_032", "PE_GP_04_033", "PE_GP_04_034", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_165", "PE_IY_03_166", "PE_IY_03_167", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "VARSEL_REVURD", "DOD_INFO_RETT_MAN", "INFO_P1", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("ALDER", asList("PE_AP_01_001", "PE_AP_01_006", "PE_AP_01_007", "PE_AP_04_001", "PE_AP_04_010", "PE_AP_04_020", "PE_AP_04_202", "PE_AP_04_203", "PE_AP_04_211", "PE_AP_04_212", "PE_AP_04_214", "PE_AP_04_215", "PE_AP_04_216", "PE_AP_04_220", "PE_AP_04_223", "PE_AP_04_224", "PE_AP_04_225", "PE_AP_04_901", "PE_AP_04_902", "PE_AP_04_903", "PE_AP_04_904", "PE_AP_04_910", "PE_AP_04_911", "PE_AP_04_912", "PE_AP_04_913", "PE_AP_04_914", "PE_AP_04_920", "PE_AP_04_922", "PE_BA_01_108", "PE_FT_01_002", "PE_FT_01_003", "PE_FT_01_006", "PE_FT_01_007", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_165", "PE_IY_03_166", "PE_IY_03_167", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_04_125", "PE_IY_04_126", "PE_IY_04_127", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_103", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_BA_04_534", "PE_UT_04_002", "VARSEL_REVURD", "AP_AVSL_FT_MAN", "AP_AVSL_ENDR", "AP_AVSL_TIDLUTTAK", "AP_AVSL_UTTAK", "DOD_INFO_RETT_MAN", "INFO_P1", "AP_INNV_MAN", "AP_OPPH_FT_MAN", "AP_AVSL_GJRETT_MAN", "AP_INNV_AVT_MAN", "AP_ENDR_GRAD_MAN", "AP_ENDR_STANS_MAN", "AP_ENDR_EPS_MAN", "AP_ENDR_FLYTT_MAN", "AP_ENDR_GJRETT_MAN", "AP_ENDR_OPPTJ_MAN", "AP_ENDR_FT_MAN", "AP_STANS_FLYTT_MAN", "AP_INFO_STID_MAN", "AP_ENDR_INST_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET","VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("GRBL", asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN"));
        sakToBrevMap.put("GENRL", asList("PE_AP_01_001", "PE_AP_01_006", "PE_GP_01_010", "PE_GP_01_011", "PE_GP_01_012", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_102", "PE_IY_05_104", "PE_IY_05_105", "PE_IY_05_111", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET"));
        sakToBrevMap.put("KRIGSP", asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN"));
        sakToBrevMap.put("AFP_PRIVAT", asList("PE_AF_04_111", "PE_AF_04_112", "PE_AF_04_114", "PE_AP_01_001", "PE_AP_04_216", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_103", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "VARSEL_REVURD", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET",  "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV", "AFP_INNV_MAN"));
    }

    public List<String> map(String saktype) {
        return applyTogglesForRemovedBrevkoder(
                saktype,
                applyTogglesForAddedBrevkoder(mapIgnoreFeatureToggle(saktype))
        );
    }

    public List<String> mapIgnoreFeatureToggle(String saktype) {
        if (sakToBrevMap.containsKey(saktype)) {
            return sakToBrevMap.get(saktype);
        } else {
            throw new IllegalArgumentException("Saktype \"" + saktype + "\" does not exist");
        }
    }

    public Set<String> getSakTyper() {
        return new HashSet<>(sakToBrevMap.keySet());
    }

    /**
     * Filtrerer bort "nye" brevkoder basert på feature toggles, altså brevkoder som skal legges til, men ikke nødvendigvis skal være synlige i alle miljø enda.
     *
     * @param brevkoder Liste som brevkoder i deaktiverte features skal fjernes fra.
     * @return Liste uten brevkoder i deaktiverte features.
     */
    private List<String> applyTogglesForAddedBrevkoder(List<String> brevkoder) {
        return brevkoder.stream().filter(brevKode -> Optional.ofNullable(addedBrevkoder.get(brevKode)).map(UnleashProvider.Toggle::isEnabled).orElse(true)).collect(toList());
    }

    /**
     * Legger til sanerte brevkoder igjen basert på feature toggles, altså brevkoder som skal fjernes, men ikke nødvendigvis i alle miljø enda.
     *
     * @param saktype Saktypen brevkoden må være med i.
     * @param brevkoder Liste ov brevkoder (hvor sanerte brevkoder allerede er fjernet)
     * @return Liste som inkluderer alle sanerte brevkoder hvor feature toggle er deaktivert.
     */
    private List<String> applyTogglesForRemovedBrevkoder(String saktype, List<String> brevkoder) {
        Set<String> reAddBrevkoder = removedBrevkoder.stream().filter(brevkode -> brevkode.saktyper.contains(saktype))
                .filter(brevkode -> brevkode.toggle.isDisabled())
                .map(brevkode -> brevkode.brevkode)
                .collect(toSet());

        return new ArrayList<>(Sets.union(reAddBrevkoder, new HashSet<>(brevkoder)));
    }

    public static class BrevkodeToRemove {
        public final String brevkode;
        public final UnleashProvider.Toggle toggle;
        public final List<String> saktyper;

        public BrevkodeToRemove(String brevkode, UnleashProvider.Toggle toggle, List<String> saktyper) {
            this.brevkode = brevkode;
            this.toggle = toggle;
            this.saktyper = saktyper;
        }
    }
}

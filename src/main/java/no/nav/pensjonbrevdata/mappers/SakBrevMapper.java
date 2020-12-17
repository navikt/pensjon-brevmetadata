package no.nav.pensjonbrevdata.mappers;
import static no.nav.pensjonbrevdata.config.BrevdataFeature.*;
import static no.nav.pensjonbrevdata.unleash.UnleashProvider.toggle;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SakBrevMapper {
    private final Map<String, List<String>> sakToBrevMap;
    private static Function<List<String>, List<String>> brevkodeFilter(String togglekey, String toggleBrevkode) {
        return brevkoder -> brevkoder.stream().filter(brevkode -> toggle(togglekey).isEnabled() || !brevkode.equals(toggleBrevkode)).collect(Collectors.toList());
    }

    private static final Function<List<String>, List<String>> filtrerBrevkoder = brevkodeFilter(ENABLE_NY_BREV_METADATA, "PE_IY_05_401_NY_BREV")
            .andThen(brevkodeFilter(ENABLE_NY_BREV_METADATA,"PE_AP_04_202_NY_BREV"))
            .andThen(brevkodeFilter(BRUK_VEDTAK_TILBAKEKREV,"VEDTAK_TILBAKEKREV"))
            .andThen(brevkodeFilter(BRUK_VEDTAK_TILBAKEKREV_MIDL,"VEDTAK_TILBAKEKREV_MIDL"));

    public SakBrevMapper() {
        sakToBrevMap = new HashMap<>();

        sakToBrevMap.put("FAM_PL", Arrays.asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "VARSEL_REVURD", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET","PE_IY_05_401_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("GAM_YRK", Arrays.asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "PE_IY_05_401_NY_BREV"));
        sakToBrevMap.put("OMSORG", Arrays.asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_001", "PE_IY_04_010", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_101", "PE_IY_05_102", "PE_IY_05_104", "PE_IY_05_105", "PE_IY_05_200", "PE_IY_05_201", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "OMSORG_EGEN_MAN", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "PE_IY_05_401_NY_BREV"));
        sakToBrevMap.put("AFP", Arrays.asList("PE_AF_03_101", "PE_AF_04_001", "PE_AF_04_010", "PE_AF_04_020", "PE_AF_04_103", "PE_AF_04_104", "PE_AF_04_105", "PE_AF_04_106", "PE_AF_04_107", "PE_AF_04_108", "PE_AF_04_109", "PE_AF_04_110", "PE_AP_01_001", "PE_BA_01_108", "PE_FT_01_001", "PE_FT_01_002", "PE_FT_01_003", "PE_FT_01_006", "PE_FT_01_007", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "VARSEL_REVURD", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "PE_IY_05_401_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("BARNEP", Arrays.asList("PE_AP_01_001", "PE_BP_01_001", "PE_BP_01_002", "PE_BP_04_001", "PE_BP_04_002", "PE_BP_04_010", "PE_BP_04_011", "PE_BP_04_020", "PE_BP_04_021", "PE_BP_04_030", "PE_BP_04_031", "PE_GP_01_010", "PE_GP_01_011", "PE_GP_01_012", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_165", "PE_IY_03_166", "PE_IY_03_167", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "VARSEL_REVURD", "DOD_INFO_RETT_MAN", "INFO_P1", "BP_AVSL_MAN", "BP_OPPH_MAN", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "PE_IY_05_401_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("UFOREP", Arrays.asList("PE_AP_01_001", "PE_BA_01_108", "PE_FT_01_001", "PE_FT_01_002", "PE_FT_01_003", "PE_FT_01_006", "PE_FT_01_007", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_169", "PE_IY_03_170", "PE_IY_03_171", "PE_IY_03_172", "PE_IY_03_173", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UP_01_001", "PE_UP_04_001", "PE_UP_04_010", "PE_UP_04_020", "PE_UP_04_021", "PE_UP_04_022", "PE_UP_04_023", "PE_UP_04_024", "PE_UP_04_025", "PE_UP_04_026", "PE_UP_04_027", "PE_UP_04_028", "PE_UP_04_029", "PE_UP_04_030", "PE_UP_07_010", "PE_UP_07_105", "PE_UP_07_100", "PE_UT_04_001", "PE_UT_04_002", "PE_UT_04_003", "PE_IY_03_167", "PE_UT_04_100", "PE_UT_04_101", "PE_UT_04_102", "PE_UT_04_103", "PE_UT_04_104", "PE_UT_04_114", "PE_UT_04_115", "PE_UT_04_117", "PE_UT_04_300", "PE_UT_04_004", "PE_UT_07_100", "PE_UT_04_401", "PE_UT_04_118", "PE_UT_04_400", "PE_UT_04_402", "PE_UT_04_106", "PE_UT_04_107", "PE_UT_04_108", "PE_UT_04_109", "PE_UT_06_300", "DOD_INFO_RETT_MAN", "INFO_P1", "AP_INFO_STID_MAN", "PE_IY_05_401_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("GJENLEV", Arrays.asList("PE_AP_01_001", "PE_GP_01_010", "PE_GP_01_011", "PE_GP_04_001", "PE_GP_04_010", "PE_GP_04_020", "PE_GP_04_022", "PE_GP_04_023", "PE_GP_04_024", "PE_GP_04_025", "PE_GP_04_026", "PE_GP_04_027", "PE_GP_04_028", "PE_GP_04_029", "PE_GP_04_030", "PE_GP_04_031", "PE_GP_04_032", "PE_GP_04_033", "PE_GP_04_034", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_165", "PE_IY_03_166", "PE_IY_03_167", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "VARSEL_REVURD", "DOD_INFO_RETT_MAN", "INFO_P1", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "PE_IY_05_401_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("ALDER", Arrays.asList("PE_AP_01_001", "PE_AP_01_006", "PE_AP_01_007", "PE_AP_04_001", "PE_AP_04_010", "PE_AP_04_020", "PE_AP_04_202", "PE_AP_04_203", "PE_AP_04_211", "PE_AP_04_212", "PE_AP_04_214", "PE_AP_04_215", "PE_AP_04_216", "PE_AP_04_220", "PE_AP_04_223", "PE_AP_04_224", "PE_AP_04_225", "PE_AP_04_901", "PE_AP_04_902", "PE_AP_04_903", "PE_AP_04_904", "PE_AP_04_910", "PE_AP_04_911", "PE_AP_04_912", "PE_AP_04_913", "PE_AP_04_914", "PE_AP_04_920", "PE_AP_04_922", "PE_BA_01_108", "PE_FT_01_001", "PE_FT_01_002", "PE_FT_01_003", "PE_FT_01_006", "PE_FT_01_007", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_165", "PE_IY_03_166", "PE_IY_03_167", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_04_125", "PE_IY_04_126", "PE_IY_04_127", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_103", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_BA_04_534", "PE_UT_04_002", "VARSEL_REVURD", "AP_AVSL_FT_MAN", "AP_AVSL_ENDR", "AP_AVSL_TIDLUTTAK", "AP_AVSL_UTTAK", "DOD_INFO_RETT_MAN", "INFO_P1", "AP_INNV_MAN", "AP_OPPH_FT_MAN", "AP_AVSL_GJRETT_MAN", "AP_INNV_AVT_MAN", "AP_ENDR_GRAD_MAN", "AP_ENDR_STANS_MAN", "AP_ENDR_EPS_MAN", "AP_ENDR_FLYTT_MAN", "AP_ENDR_GJRETT_MAN", "AP_ENDR_OPPTJ_MAN", "AP_ENDR_FT_MAN", "AP_STANS_FLYTT_MAN", "AP_INFO_STID_MAN", "AP_ENDR_INST_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "PE_IY_05_401_NY_BREV", "PE_AP_04_202_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
        sakToBrevMap.put("GRBL", Arrays.asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "PE_IY_05_401_NY_BREV"));
        sakToBrevMap.put("GENRL", Arrays.asList("PE_AP_01_001", "PE_AP_01_006", "PE_FT_01_001", "PE_GP_01_010", "PE_GP_01_011", "PE_GP_01_012", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_102", "PE_IY_05_104", "PE_IY_05_105", "PE_IY_05_111", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "PE_IY_05_401_NY_BREV"));
        sakToBrevMap.put("KRIGSP", Arrays.asList("PE_AP_01_001", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "PE_IY_05_401_NY_BREV"));
        sakToBrevMap.put("AFP_PRIVAT", Arrays.asList("PE_AF_04_111", "PE_AF_04_112", "PE_AF_04_114", "PE_AP_01_001", "PE_AP_04_216", "PE_GP_01_010", "PE_IY_03_047", "PE_IY_03_048", "PE_IY_03_049", "PE_IY_03_051", "PE_IY_03_150", "PE_IY_03_151", "PE_IY_03_152", "PE_IY_03_153", "PE_IY_03_154",  "PE_IY_03_156", "PE_IY_03_157", "PE_IY_03_158", "PE_IY_03_159", "PE_IY_03_160", "PE_IY_03_161", "PE_IY_03_162", "PE_IY_03_163", "PE_IY_03_164", "PE_IY_03_168", "PE_IY_03_174", "PE_IY_03_175", "PE_IY_03_176", "PE_IY_03_177", "PE_IY_03_178", "PE_IY_03_179", "PE_IY_03_180", "PE_IY_04_050", "PE_IY_04_051", "PE_IY_04_060", "PE_IY_04_061", "PE_IY_05_006", "PE_IY_05_007", "PE_IY_05_008", "PE_IY_05_027", "PE_IY_05_041", "PE_IY_05_300", "PE_IY_05_301", "PE_IY_05_401", "PE_IY_05_402", "PE_IY_05_410", "PE_IY_05_411", "PE_IY_06_103", "PE_IY_06_510", "PE_IY_06_511", "PE_OK_06_100", "PE_OK_06_101", "PE_OK_06_102", "PE_UT_04_002", "PE_IY_03_167", "VARSEL_REVURD", "AP_INFO_STID_MAN", "HENT_INFO_MAN", "VARSEL_TILBAKEBET", "PE_IY_05_401_NY_BREV", "VEDTAK_TILBAKEKREV_MIDL", "VEDTAK_TILBAKEKREV"));
    }

    public List<String> map(String saktype) {
        if (sakToBrevMap.containsKey(saktype)) {
            return filtrerBrevkoder.apply(sakToBrevMap.get(saktype));
        } else {
            throw new IllegalArgumentException("Saktype \"" + saktype + "\" does not exist");
        }
    }
}

package no.nav.pensjonbrevdata.helpers

import java.io.File

object DokumentmalGenerators {
    @JvmField
    val dokumentmalGenerator: (String) -> String = dokumentmalBuilder("dokumentmal")

    @JvmField
    val fellesmalGenerator: (String) -> String = dokumentmalBuilder("felles")

    private fun dokumentmalBuilder(dokumentMalType: String): (String) -> String = { dokumentmalId: String ->
        read("xsd" + File.separator + dokumentMalType + File.separator + dokumentmalId + ".xsd")
    }

    private fun read(resourcePath: String): String = javaClass.getResource("/$resourcePath").readText(Charsets.UTF_8)
}

package no.nav.pensjonbrevdata.helpers

import java.io.File

object DokumentmalGenerators {
    @JvmField
    val dokumentmalGenerator: (String) -> String = dokumentmalBuilder("dokumentmal")

    @JvmField
    val fellesmalGenerator: (String) -> String = dokumentmalBuilder("felles")

    private fun dokumentmalBuilder(dokumentMalType: String): (String) -> String = { dokumentmalId: String ->
        XsdFileReader().read("xsd" + File.separator + dokumentMalType + File.separator + dokumentmalId + ".xsd")
    }
}

package no.nav.pensjonbrevdata.helpers

import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.util.FileCopyUtils
import java.io.IOException
import java.nio.charset.StandardCharsets

class XsdFileReader {
    fun read(resourcePath: String): String {
        val resource: Resource = ClassPathResource(resourcePath)

        val inputStream = resource.inputStream

        try {
            val bdata = FileCopyUtils.copyToByteArray(inputStream)
            return String(bdata, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            throw IOException("Failed when trying to read file on path $resourcePath", e)
        } finally {
            IOUtils.closeQuietly(inputStream)
        }
    }
}

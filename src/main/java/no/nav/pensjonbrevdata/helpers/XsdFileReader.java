package no.nav.pensjonbrevdata.helpers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class XsdFileReader {

    public String read(String path) throws IOException {
        return readFile(path);
    }

    private String readFile(String resourcePath) throws IOException {
        Resource resource = new ClassPathResource(resourcePath);

        InputStream inputStream = resource.getInputStream();

        String xsd;

        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            xsd = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Failed when trying to read file on path " + resourcePath);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return xsd;
    }
}

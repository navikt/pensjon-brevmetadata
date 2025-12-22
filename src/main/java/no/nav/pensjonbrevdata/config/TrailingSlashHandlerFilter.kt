package no.nav.pensjonbrevdata.config

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.IOException

class TrailingSlashHandlerFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val path = httpRequest.getRequestURI()

        if (path.endsWith("/")) {
            val newPath = path.substring(0, path.length - 1)
            val newRequest: HttpServletRequest = CustomHttpServletRequestWrapper(httpRequest, newPath)
            chain.doFilter(newRequest, response)
        } else {
            chain.doFilter(request, response)
        }
    }

    private class CustomHttpServletRequestWrapper(request: HttpServletRequest, private val newPath: String?) :
        HttpServletRequestWrapper(request) {
        override fun getRequestURI(): String? {
            return newPath
        }

        override fun getRequestURL(): StringBuffer {
            val url = StringBuffer()
            url.append(getScheme()).append("://").append(getServerName()).append(":").append(getServerPort())
                .append(newPath)
            return url
        }
    }
}
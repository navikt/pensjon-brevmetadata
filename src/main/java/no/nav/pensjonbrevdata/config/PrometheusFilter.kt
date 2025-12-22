package no.nav.pensjonbrevdata.config

import io.prometheus.client.Counter
import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.Locale

@Component
@Order(10)
class PrometheusFilter : Filter {
    private fun skipCounting(uriPath: String): Boolean {
        return skipUris.contains(uriPath.lowercase(Locale.getDefault())) || uriPath.startsWith(swaggerUriPattern)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, chain: FilterChain) {
        val req = servletRequest as HttpServletRequest
        if (skipCounting(req.getServletPath())) {
            chain.doFilter(servletRequest, servletResponse)
            return
        }

        totalIncomingRequests.inc()

        LOGGER.info(req.getRequestURI())

        try {
            chain.doFilter(servletRequest, servletResponse)
        } finally {
            val response = servletResponse as HttpServletResponse

            if (response.getStatus() >= 300) {
                LOGGER.error(req.getRequestURI() + " failing with code " + response.getStatus())
                totalFailedRequests.inc()
                if (response.getStatus() >= 500) {
                    totalFailedRequests500.inc()
                } else if (response.getStatus() >= 400) {
                    totalFailedRequests400.inc()
                } else if (response.getStatus() >= 300) {
                    totalFailedRequests300.inc()
                }
            } else {
                totalSuccessfulRequests.inc()
            }
        }
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PrometheusFilter::class.java)

        val totalIncomingRequests: Counter = Counter.build().name("brevdata_rest_incoming_requests_total")
            .help("Number of total requests")
            .register()

        val totalFailedRequests: Counter = Counter.build().name("brevdata_rest_failed_requests_total")
            .help("Number of total failed requests (status >= 300)")
            .register()

        val totalFailedRequests300: Counter = Counter.build().name("brevdata_rest_failed_requests_300_total")
            .help("Number of total failed requests (status ~= 300)")
            .register()

        val totalFailedRequests400: Counter = Counter.build().name("brevdata_rest_failed_requests_400_total")
            .help("Number of total failed requests (status ~= 400)")
            .register()

        val totalFailedRequests500: Counter = Counter.build().name("brevdata_rest_failed_requests_500_total")
            .help("Number of total failed requests (status ~= 500)")
            .register()

        val totalSuccessfulRequests: Counter = Counter.build().name("brevdata_rest_successful_requests_total")
            .help("Number of total failed requests (status == 2xx)")
            .register()

        val skipUris: MutableList<String?> = mutableListOf<String?>(
            "/api/internal/prometheus",
            "/api/internal/isalive",
            "/api/internal/isready",
            "/api/internal/docs",
            "/api/internal/docs/swagger-config"
        )
        const val swaggerUriPattern: String = "/swagger-ui"
    }
}

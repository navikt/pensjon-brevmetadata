package no.nav.pensjonbrevdata.config;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(10)
public class PrometheusFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrometheusFilter.class);

    static final Counter totalIncomingRequests = Counter.build().name("brevdata_rest_incoming_requests_total")
            .help("Number of total requests")
            .register();

    static final Counter totalFailedRequests = Counter.build().name("brevdata_rest_failed_requests_total")
            .help("Number of total failed requests (status >= 300)")
            .register();

    static final Counter totalFailedRequests300 = Counter.build().name("brevdata_rest_failed_requests_300_total")
            .help("Number of total failed requests (status ~= 300)")
            .register();

    static final Counter totalFailedRequests400 = Counter.build().name("brevdata_rest_failed_requests_400_total")
            .help("Number of total failed requests (status ~= 400)")
            .register();

    static final Counter totalFailedRequests500 = Counter.build().name("brevdata_rest_failed_requests_500_total")
            .help("Number of total failed requests (status ~= 500)")
            .register();

    static final Counter totalSuccessfulRequests = Counter.build().name("brevdata_rest_successful_requests_total")
            .help("Number of total failed requests (status == 2xx)")
            .register();

    static final List<String> skipUris = Arrays.asList("/api/internal/prometheus", "/api/internal/isalive", "/api/internal/isready", "/api/internal/docs", "/api/internal/docs/swagger-config");
    static final String swaggerUriPattern = "/swagger-ui";

    private boolean skipCounting(String uriPath) {
        return skipUris.contains(uriPath.toLowerCase()) || uriPath.startsWith(swaggerUriPattern);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (skipCounting(req.getServletPath())) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        totalIncomingRequests.inc();

        LOGGER.info(req.getRequestURI());

        try {
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            if (response.getStatus() >= 300) {
                totalFailedRequests.inc();
                if (response.getStatus() >= 500) {
                    totalFailedRequests500.inc();
                } else if (response.getStatus() >= 400) {
                    totalFailedRequests400.inc();
                } else if (response.getStatus() >= 300) {
                    totalFailedRequests300.inc();
                }
            } else {
                totalSuccessfulRequests.inc();
            }
        }
    }
}

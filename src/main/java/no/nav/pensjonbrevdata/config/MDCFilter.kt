package no.nav.pensjonbrevdata.config;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        MDC.put("X-Correlation-Id", req.getHeader("X-Correlation-Id"));
        MDC.put("X-Transaction-Id", req.getHeader("X-Transaction-Id"));
        MDC.put("X-Nav-Call-Id", req.getHeader("Nav-Call-Id"));

        chain.doFilter(request, response);
    }
}

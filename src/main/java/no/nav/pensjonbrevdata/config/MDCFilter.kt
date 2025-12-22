package no.nav.pensjonbrevdata.config

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class MDCFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest

        MDC.put("X-Correlation-Id", req.getHeader("X-Correlation-Id"))
        MDC.put("X-Transaction-Id", req.getHeader("X-Transaction-Id"))
        MDC.put("X-Nav-Call-Id", req.getHeader("Nav-Call-Id"))

        chain.doFilter(request, response)
    }
}

package no.nav.pensjonbrevdata.config

import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterApplier {
    @Bean
    fun trailingSlashFilter(): FilterRegistrationBean<Filter> {
        val registrationBean = FilterRegistrationBean<Filter>()
        registrationBean.setFilter(TrailingSlashHandlerFilter())
        registrationBean.addUrlPatterns("/*")
        return registrationBean
    }
}

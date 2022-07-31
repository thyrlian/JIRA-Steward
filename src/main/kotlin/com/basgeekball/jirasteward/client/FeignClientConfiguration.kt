package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.properties.JiraProperties
import feign.auth.BasicAuthRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignClientConfiguration {
    @Bean
    fun basicAuthRequestInterceptor(jiraProperties: JiraProperties): BasicAuthRequestInterceptor {
        return BasicAuthRequestInterceptor(jiraProperties.auth.username, jiraProperties.auth.password)
    }
}
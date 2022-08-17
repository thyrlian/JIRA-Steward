package com.basgeekball.jirasteward.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jira")
data class JiraProperties(
    var host: String,
    var auth: AuthProperties,
    var timezone: String
)

data class AuthProperties(
    var username: String,
    var password: String
)

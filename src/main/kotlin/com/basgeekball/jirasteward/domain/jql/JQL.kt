package com.basgeekball.jirasteward.domain.jql

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class JQL(
    @JsonProperty("jql") val jql: String?
)
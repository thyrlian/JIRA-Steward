package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
        @JsonProperty("name") val username: String?,
        @JsonProperty("displayName") val name: String?,
        @JsonProperty("emailAddress") val email: String?,
        @JsonProperty("self") val link: String?
)
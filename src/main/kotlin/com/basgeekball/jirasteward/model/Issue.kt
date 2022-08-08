package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Issue(
        @JsonProperty("id") val id: Int?,
        @JsonProperty("key") val key: String?,
        @JsonProperty("fields") val fields: Fields?
)
package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Component(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("self") val link: String?,
    @JsonProperty("name") val name: String?
)
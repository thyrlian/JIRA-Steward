package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Status(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("self") val link: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("statusCategory") val category: StatusCategory?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class StatusCategory(
        @JsonProperty("id") val id: Int?,
        @JsonProperty("self") val link: String?,
        @JsonProperty("key") val key: String?,
        @JsonProperty("name") val name: String?
    )
}
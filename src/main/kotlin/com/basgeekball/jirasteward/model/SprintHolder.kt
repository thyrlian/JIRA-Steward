package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SprintHolder(
    @JsonProperty("maxResults") val maxResults: Int?,
    @JsonProperty("startAt") val startAt: Int?,
    @JsonProperty("isLast") val isLast: Boolean?,
    @JsonProperty("values") val sprints: List<Sprint>
)
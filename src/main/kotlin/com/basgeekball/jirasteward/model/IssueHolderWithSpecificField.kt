package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class IssueHolderWithSpecificField(
    @JsonProperty("startAt") val startAt: Int?,
    @JsonProperty("maxResults") val maxResults: Int?,
    @JsonProperty("total") val total: Int?,
    @JsonProperty("issues") val issues: List<IssueWithSpecificField>
)
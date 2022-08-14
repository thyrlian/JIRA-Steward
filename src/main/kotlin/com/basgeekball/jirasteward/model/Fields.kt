package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Fields(
        @JsonProperty("summary") val summary: String,
        @JsonProperty("issuetype") val type: IssueType?,
        @JsonProperty("project") val project: Project?,
        @JsonProperty("priority") val priority: Priority?,
        @JsonProperty("status") val status: Status,
        @JsonProperty("created") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") val created: Date?,
        @JsonProperty("updated") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") val updated: Date?,
        @JsonProperty("resolutiondate") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") val resolved: Date?,
        @JsonProperty("reporter") val reporter: User?,
        @JsonProperty("assignee") val assignee: User?,
        @JsonProperty("components") val components: List<Component>?,
        @JsonProperty("fixVersions") val fixVersions: List<Version>?
)
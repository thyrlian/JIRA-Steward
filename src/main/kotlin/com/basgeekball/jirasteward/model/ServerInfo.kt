package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ServerInfo(
    @JsonProperty("baseUrl") val baseUrl: String?,
    @JsonProperty("version") val version: String?,
    @JsonProperty("versionNumbers") val versionNumbers: List<Int>?,
    @JsonProperty("deploymentType") val deploymentType: String?,
    @JsonProperty("buildNumber") val buildNumber: Int?,
    @JsonProperty("buildDate") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") val buildDate: Date?,
    @JsonProperty("serverTime") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") val serverTime: Date?,
    @JsonProperty("scmInfo") val scmInfo: String?,
    @JsonProperty("serverTitle") val serverTitle: String?
)
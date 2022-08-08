package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Version(
        @JsonProperty("id") val id: Int?,
        @JsonProperty("self") val link: String?,
        @JsonProperty("name") val name: String?,
        @JsonProperty("archived") val archived: Boolean?,
        @JsonProperty("released") val released: Boolean?,
        @JsonProperty("releaseDate") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") val releaseDate: Date?
)
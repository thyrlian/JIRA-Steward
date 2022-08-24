package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Sprint(
    @JsonProperty("id") val id: Int,
    @JsonProperty("self") val self: String,
    @JsonProperty("state") val state: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("originBoardId") val originBoardId: Int,
    @JsonProperty("startDate") @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ) val startDate: Date?,
    @JsonProperty("endDate") @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ) val endDate: Date?,
    @JsonProperty("completeDate") @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ) val completeDate: Date?,
    @JsonProperty("goal") val goal: String?
)
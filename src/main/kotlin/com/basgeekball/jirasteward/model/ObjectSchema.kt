package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ObjectSchema(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String?,
    @JsonProperty("objectSchemaKey") val objectSchemaKey: String?,
    @JsonProperty("status") val status: String?,
    @JsonProperty("created") @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ) val created: Date?,
    @JsonProperty("updated") @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ) val updated: Date?,
    @JsonProperty("objectCount") val objectCount: Int?,
    @JsonProperty("objectTypeCount") val objectTypeCount: Int?
)
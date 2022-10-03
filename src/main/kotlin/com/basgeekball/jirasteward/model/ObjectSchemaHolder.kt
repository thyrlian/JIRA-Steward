package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class  ObjectSchemaHolder(
    @JsonProperty("objectschemas") val objectSchemas: List<ObjectSchema>
)
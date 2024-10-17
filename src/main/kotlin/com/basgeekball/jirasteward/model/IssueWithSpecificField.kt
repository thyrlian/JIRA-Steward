package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class IssueWithSpecificField(
    @JsonProperty("key") val key: String,
    @JsonProperty("fields") val fields: QueriedFields
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class QueriedFields(
    @JsonAnyGetter @JsonAnySetter val fieldValuePairs: MutableMap<String, Any> = mutableMapOf()
) {
    fun getSpecificFieldValue(fieldId: String): SpecificFieldValue? {
        val fieldData = fieldValuePairs[fieldId] as? Map<*, *>
        val value = fieldData?.get("value") as? String ?: return null
        return SpecificFieldValue(value)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpecificFieldValue(
    @JsonProperty("value") val value: String
)
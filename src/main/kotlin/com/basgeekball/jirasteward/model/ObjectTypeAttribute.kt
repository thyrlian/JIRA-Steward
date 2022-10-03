package com.basgeekball.jirasteward.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ObjectTypeAttribute(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String?,
    @JsonProperty("label") val label: Boolean,
    @JsonProperty("description") val description: String?,
    @JsonProperty("position") val position: Int,
    @JsonProperty("type") val type: Int?,
    @JsonProperty("defaultType") val defaultType: DefaultType?,
    @JsonProperty("indexed") val indexed: Boolean,
    @JsonProperty("system") val system: Boolean?,
    @JsonProperty("editable") val editable: Boolean?,
    @JsonProperty("sortable") val sortable: Boolean?,
    @JsonProperty("summable") val summable: Boolean?,
    @JsonProperty("removable") val removable: Boolean?,
    @JsonProperty("hidden") val hidden: Boolean?,
    @JsonProperty("uniqueAttribute") val uniqueAttribute: Boolean?,
    @JsonProperty("includeChildObjectTypes") val includeChildObjectTypes: Boolean?,
    @JsonProperty("minimumCardinality") val minimumCardinality: Int?,
    @JsonProperty("maximumCardinality") val maximumCardinality: Int?,
    @JsonProperty("options") val options: String?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class DefaultType(
        @JsonProperty("id") val id: Int,
        @JsonProperty("name") val name: String
    )
}
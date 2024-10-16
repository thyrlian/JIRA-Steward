import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FieldMetadata(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("custom") val custom: Boolean?,
    @JsonProperty("orderable") val orderable: Boolean?,
    @JsonProperty("navigable") val navigable: Boolean?,
    @JsonProperty("searchable") val searchable: Boolean?,
    @JsonProperty("clauseNames") val clauseNames: List<String>?,
    @JsonProperty("schema") val schema: Schema?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Schema(
        @JsonProperty("type") val type: String,
        @JsonProperty("system") val system: String?,
        @JsonProperty("items") val items: String?,
        @JsonProperty("custom") val custom: String?,
        @JsonProperty("customId") val customId: Int?
    )

    fun toSimpleString(): String {
        return "FieldMetadata(id='$id', name='$name'"
    }
}

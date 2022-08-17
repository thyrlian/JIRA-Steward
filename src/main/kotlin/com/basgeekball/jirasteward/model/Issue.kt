package com.basgeekball.jirasteward.model

import com.basgeekball.jirasteward.model.JiraConstants.STATUS_CATEGORY_KEY_DONE
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Issue(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("key") val key: String?,
    @JsonProperty("fields") val fields: Fields?
) {
    companion object {
        const val TIMEZONE = "UTC"
    }

    fun isDone(): Boolean {
        return fields?.status?.category?.key.equals(STATUS_CATEGORY_KEY_DONE, ignoreCase = true)
    }

    private fun calculateDaysBetweenDates(startDate: Date?, endDate: Date?, timezone: String = TIMEZONE): Long? {
        return if (startDate != null && endDate != null) {
            ChronoUnit.DAYS.between(
                startDate.toInstant().atZone(ZoneId.of(timezone)).toLocalDate(),
                endDate.toInstant().atZone(ZoneId.of(timezone)).toLocalDate()
            )
        } else {
            null
        }
    }

    private fun calculateDaysTillToday(date: Date?, timezone: String = TIMEZONE): Long? {
        return calculateDaysBetweenDates(date, Date(), timezone)
    }

    fun leadTime(timezone: String = TIMEZONE): Long? {
        return if (isDone()) {
            calculateDaysBetweenDates(fields?.created, fields?.resolved, timezone)
        } else {
            null
        }
    }

    fun silentTime(timezone: String = TIMEZONE): Long? {
        return calculateDaysTillToday(fields?.updated, timezone)
    }

    fun isClosedIn(year: Int, month: Int, timezone: String = TIMEZONE): Boolean {
        val date = fields?.resolved?.toInstant()?.atZone(ZoneId.of(timezone))?.toLocalDate()
        return date?.year == year && date.month.value == month
    }
}
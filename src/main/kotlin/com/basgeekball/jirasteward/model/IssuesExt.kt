package com.basgeekball.jirasteward.model

import com.basgeekball.jirasteward.model.JiraConstants.STATUS_CATEGORY_KEY_DONE
import com.basgeekball.jirasteward.model.JiraConstants.STATUS_CATEGORY_KEY_TODO
import com.basgeekball.jirasteward.model.JiraConstants.STATUS_CATEGORY_KEY_WIP

private fun List<Issue>.filterByStatusCategory(statusCategoryKey: String): List<Issue> {
    return this.filter { it.fields?.status?.category?.key.equals(statusCategoryKey, ignoreCase = true) }
}

fun List<Issue>.filterStatusCategoryToDo(): List<Issue> {
    return this.filterByStatusCategory(STATUS_CATEGORY_KEY_TODO)
}

fun List<Issue>.filterStatusCategoryInProgress(): List<Issue> {
    return this.filterByStatusCategory(STATUS_CATEGORY_KEY_WIP)
}

fun List<Issue>.filterStatusCategoryDone(): List<Issue> {
    return this.filterByStatusCategory(STATUS_CATEGORY_KEY_DONE)
}

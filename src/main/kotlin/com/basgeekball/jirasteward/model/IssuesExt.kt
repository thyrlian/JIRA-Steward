package com.basgeekball.jirasteward.model

private fun List<Issue>.filterByStatusCategory(statusCategoryName: String): List<Issue> {
    return this.filter { it.fields?.status?.category?.name.equals(statusCategoryName)}
}

fun List<Issue>.filterStatusCategoryToDo(): List<Issue> {
    return this.filterByStatusCategory("To Do")
}

fun List<Issue>.filterStatusCategoryInProgress(): List<Issue> {
    return this.filterByStatusCategory("In Progress")
}

fun List<Issue>.filterStatusCategoryDone(): List<Issue> {
    return this.filterByStatusCategory("Done")
}

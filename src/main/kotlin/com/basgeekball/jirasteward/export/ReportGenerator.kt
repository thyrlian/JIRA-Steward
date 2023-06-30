package com.basgeekball.jirasteward.export

import com.basgeekball.jirasteward.model.Issue

interface ReportGenerator {
    fun formatOutput(issues: List<Issue>): String
}
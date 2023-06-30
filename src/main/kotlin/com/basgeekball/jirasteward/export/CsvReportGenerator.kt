package com.basgeekball.jirasteward.export

import com.basgeekball.jirasteward.model.Issue

class CsvReportGenerator : ReportGenerator {
    override fun formatOutput(issues: List<Issue>): String {
        if (issues.isEmpty()) {
            return ""
        }

        val header = buildLineWithValues("key", "link", "summary", "issuetype", "project", "status", "created", "resolutiondate")
        val rows = issues.joinToString("\n") { issue ->
            buildLineWithValues(issue.key, issue.link(), issue.fields.summary, issue.fields.type?.name, issue.fields.project?.name, issue.fields.status.name, issue.fields.created, issue.fields.resolved)
        }

        return "${header}\n${rows}"
    }

    private inline fun <reified T> buildLineWithValues(vararg values: T?): String {
        return values.joinToString(";") { it?.toString() ?: "" }
    }
}
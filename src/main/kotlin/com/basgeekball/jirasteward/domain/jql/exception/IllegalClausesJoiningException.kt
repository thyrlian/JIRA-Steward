package com.basgeekball.jirasteward.domain.jql.exception

import com.basgeekball.jirasteward.domain.jql.JQLBuilder

class IllegalClausesJoiningException(
    clauses: MutableList<String>,
    newClause: String,
    message: String = frameMessage(clauses, newClause)
) : Exception(message) {
    companion object {
        private fun frameMessage(clauses: MutableList<String>, newClause: String): String {
            return """${System.lineSeparator()}
                |   None of these join keywords: ${JQLBuilder.JOIN_KEYWORDS.values} are found.
                |   The last clause is: "${clauses.last()}"
                |   The new clause is: "$newClause"
                |   The complete clauses are: ${clauses + newClause}
            """.trimMargin()
        }
    }
}
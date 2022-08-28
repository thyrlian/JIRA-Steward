package com.basgeekball.jirasteward.domain.jql

import com.basgeekball.jirasteward.domain.jql.exception.EmptyListException
import com.basgeekball.jirasteward.domain.jql.exception.IllegalClausesJoiningException

class JQLBuilder {
    private var clauses = mutableListOf<String>()

    companion object {
        val JOIN_KEYWORDS: HashMap<String, String> = hashMapOf("and" to "AND", "or" to "OR")
    }

    //region JQL operators
    // https://support.atlassian.com/jira-software-cloud/docs/advanced-search-reference-jql-operators/
    // ================================================================================
    private fun compare(
        field: String,
        operator: String,
        vararg values: String,
        valueInListForm: Boolean = false
    ): JQLBuilder {
        // use quotation marks around strings that contain spaces
        val quotedValues = values.map {
            if (it.contains(" ") && !Regex("^\".*\"$").matches(it)) "\"$it\"" else it
        }
        // compose clause
        val mergedValues = if (quotedValues.isEmpty()) {
            ""
        } else if (quotedValues.size == 1 && !valueInListForm) {
            quotedValues.first()
        } else {
            quotedValues.joinToString(prefix = "(", postfix = ")", separator = ",")
        }
        val newClause = "$field $operator" + if (mergedValues.isEmpty()) "" else " $mergedValues"
        // validate
        if (clauses.isNotEmpty() && !JOIN_KEYWORDS.containsValue(clauses.last())) {
            throw IllegalClausesJoiningException(clauses, newClause)
        }
        clauses.add(newClause)
        return this
    }

    private fun validateList(vararg values: String) {
        if (values.isEmpty()) throw EmptyListException()
    }

    fun equal(field: String, value: String): JQLBuilder {
        return compare(field, "=", value)
    }

    fun notEqual(field: String, value: String): JQLBuilder {
        return compare(field, "!=", value)
    }

    fun greaterThan(field: String, value: String): JQLBuilder {
        return compare(field, ">", value)
    }

    fun greaterThanOrEqualTo(field: String, value: String): JQLBuilder {
        return compare(field, ">=", value)
    }

    fun lessThan(field: String, value: String): JQLBuilder {
        return compare(field, "<", value)
    }

    fun lessThanOrEqualTo(field: String, value: String): JQLBuilder {
        return compare(field, "<=", value)
    }

    fun inEitherOf(field: String, vararg values: String): JQLBuilder {
        validateList(*values)
        return compare(field, "in", *values, valueInListForm = true)
    }

    fun notInAnyOf(field: String, vararg values: String): JQLBuilder {
        validateList(*values)
        return compare(field, "not in", *values, valueInListForm = true)
    }

    fun contain(field: String, value: String): JQLBuilder {
        return compare(field, "~", value)
    }

    fun containWildcardMatch(field: String, value: String): JQLBuilder {
        return compare(field, "~", "\"$value*\"")
    }

    fun containMultipleWords(field: String, vararg values: String): JQLBuilder {
        validateList(*values)
        return compare(field, "~", "\"${values.joinToString(" ")}\"")
    }

    fun containExactPhrase(field: String, value: String): JQLBuilder {
        return compare(field, "~", "\"\\\"$value\\\"\"")
    }

    fun notContain(field: String, value: String): JQLBuilder {
        return compare(field, "!~", value)
    }

    fun haveNoValue(field: String): JQLBuilder {
        return compare(field, "is", "empty")
    }

    fun haveAValue(field: String): JQLBuilder {
        return compare(field, "is not", "empty")
    }

    fun was(field: String, value: String): JQLBuilder {
        return compare(field, "WAS", value)
    }

    fun wasIn(field: String, vararg values: String): JQLBuilder {
        validateList(*values)
        return compare(field, "WAS IN", *values, valueInListForm = true)
    }

    fun wasNotIn(field: String, vararg values: String): JQLBuilder {
        validateList(*values)
        return compare(field, "WAS NOT IN", *values, valueInListForm = true)
    }

    fun wasNot(field: String, value: String): JQLBuilder {
        return compare(field, "WAS NOT", value)
    }

    fun changed(field: String): JQLBuilder {
        return compare(field, "CHANGED")
    }
    // ================================================================================
    //endregion

    //region JQL keywords
    // https://support.atlassian.com/jira-software-cloud/docs/advanced-search-reference-jql-keywords/
    // ================================================================================
    private fun join(keyword: String): JQLBuilder {
        // validate
        if (clauses.isNotEmpty() && !JOIN_KEYWORDS.containsValue(clauses.last())) clauses.add(keyword)
        return this
    }

    fun and(): JQLBuilder {
        return join(JOIN_KEYWORDS["and"]!!)
    }

    fun or(): JQLBuilder {
        return join(JOIN_KEYWORDS["or"]!!)
    }
    // ================================================================================
    //endregion

    //region convenient helper
    // ================================================================================
    fun inProject(project: String): JQLBuilder {
        return and().equal("project", project)
    }

    fun withIssueType(type: String): JQLBuilder {
        return and().equal("type", type)
    }

    fun reportBy(user: String): JQLBuilder {
        return and().equal("reporter", user)
    }

    fun assignTo(user: String): JQLBuilder {
        return and().equal("assignee", user)
    }

    fun inSprint(sprint: String): JQLBuilder {
        return and().equal("sprint", sprint)
    }
    // ================================================================================
    //endregion

    fun build(): JQL {
        var query = ""
        clauses.forEach {
            query = query.plus(" $it")
        }
        return JQL(query.trim())
    }
}
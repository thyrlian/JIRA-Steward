package com.basgeekball.jirasteward.service

import FieldMetadata
import com.basgeekball.jirasteward.client.JiraClient
import com.basgeekball.jirasteward.domain.jql.JQL
import com.basgeekball.jirasteward.domain.jql.JQLBuilder
import com.basgeekball.jirasteward.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JiraService {
    @Autowired
    private lateinit var jiraClient: JiraClient

    fun getAllFields(fieldName: String): List<FieldMetadata> {
        return jiraClient.getAllFields()
    }

    fun getField(fieldName: String): FieldMetadata? {
        val fields = jiraClient.getAllFields()
        return fields.find { it.name == fieldName }
    }

    // There is a known issue with Atlassian's API when pulling sprints from a given board
    // https://community.atlassian.com/t5/Jira-questions/originBoardId-in-Sprint-does-not-map-back-to-id-in-Board/qaq-p/1461728
    fun getAllSprintsFromBoard(boardId: Int, state: String = ""): List<Sprint> {
        val sprints = mutableListOf<Sprint>()
        var isCompleted = false
        var startAt = 0
        while (!isCompleted) {
            val sprintHolder = jiraClient.getSprints(boardId, startAt)
            isCompleted = sprintHolder.isLast!!
            if (!isCompleted) startAt = sprintHolder.startAt!! + sprintHolder.maxResults!!
            sprints.addAll(sprintHolder.sprints!!)
        }
        sprints.retainAll { it.originBoardId == boardId }
        return if (state.isEmpty()) {
            sprints
        } else {
            sprints.filter { it.state.equals(state) }
        }
    }

    fun getPreviousSprintFromBoard(boardId: Int): Sprint? {
        val sprints = getAllSprintsFromBoard(boardId, "closed")
        return if (sprints.isEmpty()) {
            null
        } else {
            sprints.sortedBy { it.endDate }.last()
        }
    }

    fun getOngoingSprintFromBoard(boardId: Int): Sprint? {
        return jiraClient.getActiveSprints(boardId).sprints?.firstOrNull()
    }

    fun getIssues(jql: JQL): IssueHolder {
        return jiraClient.getIssues(jql)
    }

    fun getIssuesFromASprint(sprintId: String): IssueHolder {
        return getIssues(JQLBuilder().inSprint(sprintId).build())
    }

    fun getStoriesFromASprint(sprintId: String): IssueHolder {
        return getIssues(JQLBuilder().inSprint(sprintId).withIssueType("User Story").build())
    }

    fun getCompletedStoriesFromASprint(sprintId: String): IssueHolder {
        return getIssues(JQLBuilder().inSprint(sprintId).withIssueType("User Story").isCompleted().build())
    }

    fun getIssuesWithFieldValue(issueIds: List<String>, fieldName: String): List<IssueWithSpecificField> {
        val fields = jiraClient.getAllFields()
        val fieldMetadata = fields.find { it.name == fieldName }
        val fieldId = fieldMetadata?.id ?: return emptyList()
        
        val jql = issueIds.joinToString(separator = ",", prefix = "key in (", postfix = ")")
        val issueHolder = jiraClient.getIssuesWithFieldValue(jql, fieldId)
        
        return issueHolder.issues.map { issue ->
            val specificFieldValue = issue.fields.getSpecificFieldValue(fieldId)
            val fieldValueMap = specificFieldValue?.let { mapOf("value" to it.value) } ?: emptyMap()
            IssueWithSpecificField(
                key = issue.key,
                fields = QueriedFields(mutableMapOf(fieldId to fieldValueMap))
            )
        }
    }
}

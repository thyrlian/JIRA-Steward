package com.basgeekball.jirasteward.service

import com.basgeekball.jirasteward.client.JiraClient
import com.basgeekball.jirasteward.domain.jql.JQL
import com.basgeekball.jirasteward.domain.jql.JQLBuilder
import com.basgeekball.jirasteward.model.IssueHolder
import com.basgeekball.jirasteward.model.Sprint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JiraService {
    @Autowired
    private lateinit var jiraClient: JiraClient

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
        return getAllSprintsFromBoard(boardId, "active").firstOrNull()
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
}
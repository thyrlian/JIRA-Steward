package com.basgeekball.jirasteward.service

import com.basgeekball.jirasteward.client.JiraClient
import com.basgeekball.jirasteward.domain.jql.JQL
import com.basgeekball.jirasteward.model.IssueHolder
import com.basgeekball.jirasteward.model.Sprint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JiraService {
    @Autowired
    private lateinit var jiraClient: JiraClient

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
        return if (state.isEmpty()) {
            sprints
        } else {
            sprints.filter { it.state.equals(state) }
        }
    }

    fun getIssues(jql: JQL): IssueHolder {
        return jiraClient.getIssues(jql)
    }
}
package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.domain.jql.JQL
import com.basgeekball.jirasteward.model.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(value = "jira", configuration = [FeignClientConfiguration::class], url = "\${jira.host}")
interface JiraClient {
    companion object {
        const val MAX_RESULTS = 500
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/serverInfo"])
    fun getServerInfo() : ServerInfo

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/statuscategory"])
    fun getStatusCategory() : List<Status.StatusCategory>

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/project"])
    fun getProjects() : List<Project>

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/issue/{issueKey}"])
    fun getIssue(@PathVariable("issueKey") issueKey: String) : Issue

    @RequestMapping(method = [RequestMethod.POST], value = ["\${jira.api.standard}/search"])
    fun getIssues(@RequestBody jql: JQL) : IssueHolder

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.agile}/board/{boardId}/sprint"])
    fun getSprints(@PathVariable("boardId") boardId: Int, @RequestParam(name = "startAt", required = false) startAt: Int = 0, @RequestParam(name = "maxResults", required = false) maxResults: Int = MAX_RESULTS) : SprintHolder
}

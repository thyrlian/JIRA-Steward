package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.domain.jql.JQL
import com.basgeekball.jirasteward.model.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "jira", configuration = [FeignClientConfiguration::class], url = "\${jira.host}")
interface JiraClient {
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
}

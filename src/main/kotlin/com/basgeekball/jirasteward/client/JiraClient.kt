package com.basgeekball.jirasteward.client

import FieldMetadata
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
    fun getServerInfo(): ServerInfo

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/statuscategory"])
    fun getStatusCategory(): List<Status.StatusCategory>

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/project"])
    fun getProjects(): List<Project>

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/issue/{issueKey}"])
    fun getIssue(@PathVariable("issueKey") issueKey: String): Issue

    @RequestMapping(method = [RequestMethod.POST], value = ["\${jira.api.standard}/search"])
    fun getIssues(@RequestBody jql: JQL): IssueHolder

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.agile}/board/{boardId}/sprint"])
    fun getSprints(
        @PathVariable("boardId") boardId: Int,
        @RequestParam(name = "startAt", required = false) startAt: Int = 0,
        @RequestParam(name = "maxResults", required = false) maxResults: Int = MAX_RESULTS
    ): SprintHolder

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.agile}/board/{boardId}/sprint"])
    fun getActiveSprints(
        @PathVariable("boardId") boardId: Int,
        @RequestParam(name = "state", required = true) state: String = "active"
    ): SprintHolder

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.agile}/sprint/{sprintId}"])
    fun getASprint(@PathVariable("sprintId") sprintId: Int): Sprint

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.agile}/sprint/{sprintId}/issue"])
    fun getIssuesForASprint(
        @PathVariable("sprintId") sprintId: Int,
        @RequestParam(name = "startAt", required = false) startAt: Int = 0,
        @RequestParam(name = "maxResults", required = false) maxResults: Int = MAX_RESULTS
    ): IssueHolder

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.insight}/objectschema/list"])
    fun getObjectSchemaList(): ObjectSchemaHolder

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.insight}/objectschema/{objectSchemaId}"])
    fun getObjectSchema(@PathVariable("objectSchemaId") objectSchemaId: Int): ObjectSchema

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.insight}/objectschema/{objectSchemaId}/attributes"])
    fun getObjectSchemaAttributes(@PathVariable("objectSchemaId") objectSchemaId: Int): List<ObjectTypeAttribute>

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.standard}/field"])
    fun getAllFields(): List<FieldMetadata>
}

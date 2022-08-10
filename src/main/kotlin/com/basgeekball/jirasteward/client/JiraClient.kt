package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.model.Status
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "jira", configuration = [FeignClientConfiguration::class], url = "\${jira.host}")
interface JiraClient {
    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.common}/serverInfo"])
    fun getServerInfo() : String

    @RequestMapping(method = [RequestMethod.GET], value = ["\${jira.api.common}/statuscategory"])
    fun getStatusCategory() : List<Status.StatusCategory>
}

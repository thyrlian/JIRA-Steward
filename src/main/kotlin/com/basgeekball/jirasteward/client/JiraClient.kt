package com.basgeekball.jirasteward.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "jira", configuration = [FeignClientConfiguration::class], url = "\${jira.host}")
interface JiraClient {
    @RequestMapping(method = [RequestMethod.GET], value = ["/api/2/serverInfo"])
    fun getServerInfo() : String
}

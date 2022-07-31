package com.basgeekball.jirasteward

import com.basgeekball.jirasteward.properties.JiraProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableConfigurationProperties(JiraProperties::class)
@EnableFeignClients
class JiraStewardApplication

fun main(args: Array<String>) {
	runApplication<JiraStewardApplication>(*args)
}

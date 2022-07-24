package com.basgeekball.jirasteward

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JiraStewardApplication

fun main(args: Array<String>) {
	runApplication<JiraStewardApplication>(*args)
}

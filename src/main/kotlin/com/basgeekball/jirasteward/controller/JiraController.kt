package com.basgeekball.jirasteward.controller

import com.basgeekball.jirasteward.client.JiraClient
import com.basgeekball.jirasteward.model.Sprint
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class JiraController {
    @Autowired
    private lateinit var jiraClient: JiraClient
    private val mapper = jacksonObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    private fun getAllSprintsFromBoard(boardId: Int, state: String = ""): List<Sprint> {
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

    @GetMapping(value = ["/boards/{boardId}/sprints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getSprints(
        @PathVariable boardId: Int,
        @RequestParam("state") state: Optional<String>
    ): ResponseEntity<String> {
        val sprints = getAllSprintsFromBoard(boardId, state.orElse(""))
        val response = mapper.writeValueAsString(sprints)
        return ResponseEntity.ok(response)
    }
}
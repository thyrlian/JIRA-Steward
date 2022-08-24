package com.basgeekball.jirasteward.controller

import com.basgeekball.jirasteward.client.JiraClient
import com.basgeekball.jirasteward.model.Sprint
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class JiraController {
    @Autowired
    private lateinit var jiraClient: JiraClient
    private val mapper = jacksonObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    private fun getAllSprintsFromBoard(boardId: Int): List<Sprint> {
        val sprints = mutableListOf<Sprint>()
        var isCompleted = false
        var startAt = 0
        while (!isCompleted) {
            val sprintHolder = jiraClient.getSprints(boardId, startAt)
            isCompleted = sprintHolder.isLast!!
            if (!isCompleted) startAt = sprintHolder.startAt!! + sprintHolder.maxResults!!
            sprints.addAll(sprintHolder.sprints!!)
        }
        return sprints
    }

    private fun getSprintsByState(boardId: Int, state: String): List<Sprint> {
        return getAllSprintsFromBoard(boardId).filter { it.state.equals(state) }
    }

    @GetMapping(value = ["/boards/{boardId}/sprints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getAllSprints(@PathVariable boardId: Int): ResponseEntity<String> {
        val response = mapper.writeValueAsString(getAllSprintsFromBoard(boardId))
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/boards/{boardId}/activesprints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getActiveSprints(@PathVariable boardId: Int): ResponseEntity<String> {
        val response = mapper.writeValueAsString(getSprintsByState(boardId, "active"))
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/boards/{boardId}/closedsprints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getClosedSprints(@PathVariable boardId: Int): ResponseEntity<String> {
        val response = mapper.writeValueAsString(getSprintsByState(boardId, "closed"))
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/boards/{boardId}/futuresprints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getFutureSprints(@PathVariable boardId: Int): ResponseEntity<String> {
        val response = mapper.writeValueAsString(getSprintsByState(boardId, "future"))
        return ResponseEntity.ok(response)
    }
}
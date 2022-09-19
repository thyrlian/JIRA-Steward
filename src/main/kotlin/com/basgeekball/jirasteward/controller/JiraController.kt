package com.basgeekball.jirasteward.controller

import com.basgeekball.jirasteward.service.JiraService
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
    private lateinit var jiraService: JiraService
    private val mapper = jacksonObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    @GetMapping(value = ["/boards/{boardId}/sprints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getSprints(
        @PathVariable boardId: Int,
        @RequestParam("state") state: Optional<String>
    ): ResponseEntity<String> {
        val sprints = jiraService.getAllSprintsFromBoard(boardId, state.orElse(""))
        val response = mapper.writeValueAsString(sprints)
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/boards/{boardId}/previous-sprint"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getPreviousSprint(@PathVariable boardId: Int): ResponseEntity<String> {
        val sprint = jiraService.getPreviousSprintFromBoard(boardId)
        val response = mapper.writeValueAsString(sprint)
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/boards/{boardId}/ongoing-sprint"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getOngoingSprint(@PathVariable boardId: Int): ResponseEntity<String> {
        val sprint = jiraService.getOngoingSprintFromBoard(boardId)
        val response = mapper.writeValueAsString(sprint)
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/sprints/{sprintId}/issues"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getIssuesFromASprint(@PathVariable sprintId: String): ResponseEntity<String> {
        val issues = jiraService.getIssuesFromASprint(sprintId)
        val response = mapper.writeValueAsString(issues)
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/sprints/{sprintId}/stories"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getStoriesFromASprint(@PathVariable sprintId: String): ResponseEntity<String> {
        val issues = jiraService.getStoriesFromASprint(sprintId)
        val response = mapper.writeValueAsString(issues)
        return ResponseEntity.ok(response)
    }

    @GetMapping(value = ["/sprints/{sprintId}/completed-stories"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getCompletedStoriesFromASprint(@PathVariable sprintId: String): ResponseEntity<String> {
        val issues = jiraService.getCompletedStoriesFromASprint(sprintId)
        val response = mapper.writeValueAsString(issues)
        return ResponseEntity.ok(response)
    }
}
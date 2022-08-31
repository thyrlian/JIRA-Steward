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
}
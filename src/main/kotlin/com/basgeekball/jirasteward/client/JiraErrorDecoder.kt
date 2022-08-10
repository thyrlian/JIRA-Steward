package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.client.exception.BadRequestException
import com.basgeekball.jirasteward.client.exception.NotFoundException
import feign.Response
import feign.codec.ErrorDecoder

class JiraErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response): Exception {
        return when (response.status()) {
            400 -> BadRequestException()
            404 -> NotFoundException()
            else -> Exception("Generic error")
        }
    }
}
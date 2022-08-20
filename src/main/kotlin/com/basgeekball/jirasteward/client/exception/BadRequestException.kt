package com.basgeekball.jirasteward.client.exception

class BadRequestException : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)

    override fun toString(): String {
        return "BadRequestException: $message"
    }
}
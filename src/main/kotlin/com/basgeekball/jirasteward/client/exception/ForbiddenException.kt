package com.basgeekball.jirasteward.client.exception

class ForbiddenException : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)

    override fun toString(): String {
        return "ForbiddenException: $message"
    }
}
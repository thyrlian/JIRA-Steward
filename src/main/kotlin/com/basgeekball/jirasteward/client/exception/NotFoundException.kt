package com.basgeekball.jirasteward.client.exception

class NotFoundException : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)

    override fun toString(): String {
        return "NotFoundException: $message"
    }
}
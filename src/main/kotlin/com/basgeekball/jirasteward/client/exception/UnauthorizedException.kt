package com.basgeekball.jirasteward.client.exception

class UnauthorizedException : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)

    override fun toString(): String {
        return "UnauthorizedException: $message"
    }
}
package com.jmarkstar.princestheatre.repositories.exceptions

class NetworkException(
    val code: Int? = null,
    override val message: String? = null
) : Exception(message)

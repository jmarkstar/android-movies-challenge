package com.jmarkstar.princestheatre.repositories.network.response

import com.google.gson.annotations.SerializedName

data class NotFoundErrorResponse(
    val statusCode: Int,
    @SerializedName("body") val body: GenericApiError
)

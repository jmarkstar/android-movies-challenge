package com.jmarkstar.princestheatre.repositories.network.interceptors

import com.jmarkstar.princestheatre.common.Constants
import com.jmarkstar.princestheatre.common.security.Passphrases
import okhttp3.Interceptor

class AddApiKeyInterceptor : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ) = chain.request().newBuilder()
        .addHeader(Constants.API_KEY_HEADER_NAME, Passphrases.apyKey)
        .build()
        .let(chain::proceed)
}

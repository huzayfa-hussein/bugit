package com.hu.bugit.data.remote.interceptor

import com.hu.bugit.BuildConfig
import com.hu.bugit.data.common.Constants.APPLICATION_JSON
import com.hu.bugit.data.common.Constants.AUTHORIZATION
import com.hu.bugit.data.common.Constants.BEARER
import com.hu.bugit.data.common.Constants.CONTENT_TYPE
import com.hu.bugit.data.common.Constants.NOTION_VERSION
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Represents an interceptor for adding headers to network requests.
 */
class ServiceHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        when (request.header("Authentication")) {
            "IMAGE" -> {
                builder.header(AUTHORIZATION, "Client-ID ${BuildConfig.IMGUR_CLIENT_ID}")
            }

            else -> {
                builder.addHeader(CONTENT_TYPE, APPLICATION_JSON)
                builder.addHeader(NOTION_VERSION, "2022-06-28")
                builder.header(AUTHORIZATION, "$BEARER ${BuildConfig.NOTION_SECRET_KEY}")
            }
        }

        return chain.proceed(builder.build())
    }
}
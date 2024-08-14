package com.hu.bugit.data.interceptor

import com.hu.bugit.data.common.Constants.APPLICATION_JSON
import com.hu.bugit.data.common.Constants.AUTHORIZATION
import com.hu.bugit.data.common.Constants.BEARER
import com.hu.bugit.data.common.Constants.CONTENT_TYPE
import com.hu.bugit.data.common.Constants.NOTION_SECRET
import com.hu.bugit.data.common.Constants.NOTION_VERSION
import okhttp3.Interceptor
import okhttp3.Response

class NotionHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader(CONTENT_TYPE, APPLICATION_JSON)
        builder.addHeader(NOTION_VERSION, "2022-06-28")
        builder.addHeader(AUTHORIZATION, "$BEARER $NOTION_SECRET")

        return chain.proceed(builder.build())
    }
}
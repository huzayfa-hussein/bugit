package com.hu.bugit.data.remote

import com.hu.bugit.data.dto.CreateNotionPageDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotionApiService {

    @POST("pages")
    suspend fun createPage(@Body request: RequestBody): Response<CreateNotionPageDto>
}
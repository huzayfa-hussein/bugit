package com.hu.bugit.data.remote

import com.hu.bugit.data.dto.CreateNotionPageDto
import com.hu.bugit.data.request.notion.CreateNotionPageRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotionApiService {

    @POST("pages")
    suspend fun createPage(@Body request: CreateNotionPageRequest): Response<CreateNotionPageDto>
}
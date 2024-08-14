package com.hu.bugit.domain.repository

import com.hu.bugit.data.dto.CreateNotionPageDto
import com.hu.bugit.data.request.notion.CreateNotionPageRequest
import retrofit2.Response

interface NotionRepository {

    suspend fun createPage(request: CreateNotionPageRequest): Response<CreateNotionPageDto>
}
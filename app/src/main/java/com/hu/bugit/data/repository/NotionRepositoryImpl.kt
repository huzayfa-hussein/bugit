package com.hu.bugit.data.repository

import com.hu.bugit.data.dto.CreateNotionPageDto
import com.hu.bugit.data.request.notion.CreateNotionPageRequest
import com.hu.bugit.domain.repository.NotionRepository
import retrofit2.Response

class NotionRepositoryImpl : NotionRepository {
    override suspend fun createPage(request:
                                    CreateNotionPageRequest
    ): Response<CreateNotionPageDto> {
        TODO("Not yet implemented")
    }
}
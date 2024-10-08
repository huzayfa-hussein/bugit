package com.hu.bugit.domain.repository.bugForm

import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.models.bugForm.NotionPageModel

interface BugFormRepository {

    suspend fun createBug(
        title: String,
        description: String,
        imageUrl: String,
        platform: BugPlatform
    ): DomainBaseModel<NotionPageModel>

}
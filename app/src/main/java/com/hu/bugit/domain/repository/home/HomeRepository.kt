package com.hu.bugit.domain.repository.home

import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.models.home.BugModel

interface HomeRepository {

    suspend fun fetchBugList(): DomainBaseModel<List<BugModel>>
}
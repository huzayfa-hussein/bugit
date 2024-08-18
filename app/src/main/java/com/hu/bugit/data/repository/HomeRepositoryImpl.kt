package com.hu.bugit.data.repository

import com.hu.bugit.data.persistence.BugDao
import com.hu.bugit.data.persistence.entity.BugData
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.models.home.BugModel
import com.hu.bugit.domain.repository.home.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val bugDao: BugDao
) : HomeRepository {

    override suspend fun fetchBugList(): DomainBaseModel<List<BugModel>> {
        val result = bugDao.getAllBugs()
        val mapper: (List<BugData>) -> List<BugModel> = {
            it.map { data ->
                BugModel(
                    id = data.id,
                    title = data.title,
                    description = data.description,
                    imageUrl = data.imageUrl,
                    platform = data.platform,
                    date = data.createdAt,
                    url = data.url
                )
            }
        }
        return DomainBaseModel(isSuccessful = true, data = mapper(result), message = "")
    }
}
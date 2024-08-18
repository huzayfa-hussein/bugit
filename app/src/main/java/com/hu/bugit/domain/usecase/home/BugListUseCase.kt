package com.hu.bugit.domain.usecase.home

import com.hu.bugit.data.common.Resource
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.models.home.BugModel
import com.hu.bugit.domain.repository.home.HomeRepository
import com.hu.bugit.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents a use case for fetching a list of bugs.
 *@property homeRepository The repository responsible for handling home-related operations.
 */
class BugListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) : UseCase<DomainBaseModel<List<BugModel>>>() {

    override fun tagName(): String {
        return "BugListUseCase"
    }

    /**
     * Executes the use case to fetch a list of bugs.
     */
    operator fun invoke(): Flow<Resource<DomainBaseModel<List<BugModel>>>> = executeFlow {
        it.emit(Resource.Loading())
        val result = homeRepository.fetchBugList()
        if (result.isSuccessful) {
            it.emit(Resource.Success(result))
        } else {
            it.emit(Resource.Error(result.message ?: ""))
        }
    }
}
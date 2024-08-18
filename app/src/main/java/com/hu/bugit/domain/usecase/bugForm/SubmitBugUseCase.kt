package com.hu.bugit.domain.usecase.bugForm

import com.hu.bugit.data.common.Resource
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.models.bugForm.NotionPageModel
import com.hu.bugit.domain.repository.bugForm.BugFormRepository
import com.hu.bugit.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents a use case for submitting a bug report.
 * @property bugFormRepository The repository responsible for handling bug form-related operations.
 */
class SubmitBugUseCase @Inject constructor(
    private val bugFormRepository: BugFormRepository
) : UseCase<DomainBaseModel<NotionPageModel>>() {

    override fun tagName(): String {
        return "SubmitBugUseCase"
    }

    /**
     * Executes the use case to submit a bug report.
     */
    operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
        platform: BugPlatform
    ): Flow<Resource<DomainBaseModel<NotionPageModel>>> = executeFlow {
        it.emit(Resource.Loading())
//        val result = bugFormRepository.createBug(
//            title, description, imageUrl, platform
//        )
//        if (result.isSuccessful) {
//            it.emit(Resource.Success(result))
//        } else {
//            it.emit(Resource.Error(result.message ?: ""))
//        }
    }
}
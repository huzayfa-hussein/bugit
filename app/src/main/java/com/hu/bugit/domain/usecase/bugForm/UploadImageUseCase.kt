package com.hu.bugit.domain.usecase.bugForm

import com.hu.bugit.data.common.Resource
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.repository.bugForm.ImageRepository
import com.hu.bugit.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents a use case for uploading an image.
 */
class UploadImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : UseCase<DomainBaseModel<String>>() {

    override fun tagName(): String {
        return "UploadImageUseCase"
    }

    /**
     * Executes the use case to upload an image.
     * @param imagePath The path of the image to be uploaded.
     */
    operator fun invoke(
        imagePath: String
    ): Flow<Resource<DomainBaseModel<String>>> = executeFlow {
        it.emit(Resource.Loading())
        val result = imageRepository.uploadImage(imagePath)
        if (result.isSuccessful) {
            it.emit(Resource.Success(result))
        } else {
            it.emit(Resource.Error(result.message ?: ""))
        }
    }
}
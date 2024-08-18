package com.hu.bugit.data.repository

import com.hu.bugit.data.dto.ImageDto
import com.hu.bugit.data.remote.ImageApiService
import com.hu.bugit.data.remote.handler.ApiHandler
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.repository.bugForm.ImageRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApiService: ImageApiService,
    private val apiHandler: ApiHandler
) : ImageRepository {


    override suspend fun uploadImage(imagePath: String): DomainBaseModel<String> {
        val file = File(imagePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val dataMapper: (ImageDto?) -> String? = {
            it?.data?.link
        }
        return apiHandler.handleApiCall(
            {
                imageApiService.uploadImage(imagePart)
            }, dataMapper
        )
    }
}
package com.hu.bugit.data.remote

import com.hu.bugit.data.dto.ImageDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Represents an API service for uploading images.
 */
interface ImageApiService {

    @Multipart
    @POST("upload")
    @Headers("Authentication: IMAGE")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ImageDto>
}
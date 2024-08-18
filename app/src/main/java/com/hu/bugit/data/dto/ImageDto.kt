package com.hu.bugit.data.dto

import java.io.Serializable

data class ImageDto(
    val success: Boolean,
    val status: Int,
    val data: ImageData?
) : Serializable

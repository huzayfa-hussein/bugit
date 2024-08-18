package com.hu.bugit.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreateNotionPageDto(
    val id: String,
    @SerializedName("object")
    val type: String,
    val url: String?,
    @SerializedName("public_url")
    val publicUrl: String?
) : Serializable

package com.hu.bugit.data.request.notion

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Parent(
    @SerializedName("database_id")
    val databaseId: String
) : Serializable

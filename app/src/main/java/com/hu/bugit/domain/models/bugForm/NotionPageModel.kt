package com.hu.bugit.domain.models.bugForm

import java.io.Serializable

data class NotionPageModel(
    val notionUrl: String,
    val publicNotionUrl: String
) : Serializable

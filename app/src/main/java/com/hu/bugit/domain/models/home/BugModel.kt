package com.hu.bugit.domain.models.home

import androidx.compose.ui.graphics.Color
import com.hu.bugit.domain.models.BugPlatform
import java.io.Serializable

data class BugModel(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val platform: BugPlatform,
    val date: String,
    val statusColor: Color = Color.Green,
    val url: String
) : Serializable

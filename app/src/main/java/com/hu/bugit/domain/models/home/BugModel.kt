package com.hu.bugit.domain.models.home

import androidx.compose.ui.graphics.Color
import com.hu.bugit.domain.models.BugPlatform
import java.io.Serializable

/**
 * Data class representing a bug model with details about the bug.
 * @property id The unique identifier of the bug.
 * @property title The title of the bug.
 * @property description The description of the bug.
 * @property imageUrl The URL of the image associated with the bug.
 * @property platform The platform where the bug was reported.
 * @property date The date when the bug was reported.
 * @property statusColor The color representing the status of the bug.
 * @property url The URL of the bug report.
 */
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

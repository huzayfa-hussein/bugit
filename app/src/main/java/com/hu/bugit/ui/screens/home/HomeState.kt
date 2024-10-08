package com.hu.bugit.ui.screens.home

import com.hu.bugit.domain.models.home.BugModel
import java.io.Serializable

/**
 * Data class representing the state of the Home screen.
 */
data class HomeState(
    val isLoading: Boolean = false,
    val bugList: List<BugModel> = listOf(),
    val error: String = "",
    val isImageReceived: Boolean = false
) : Serializable

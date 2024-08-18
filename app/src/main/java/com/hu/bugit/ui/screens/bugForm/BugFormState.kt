package com.hu.bugit.ui.screens.bugForm

import android.net.Uri
import java.io.Serializable

/**
 * Data class representing the state of the BugForm screen.
 */
data class BugFormState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val imageUri: Uri? = null,
    val imageFilePath: String = "",
    val isSubmitted: Boolean = false,
    val error: String = "",
    val showDialog: Boolean = false,
    val dialogMessage: String = ""
) : Serializable
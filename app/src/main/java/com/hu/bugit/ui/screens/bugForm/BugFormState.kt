package com.hu.bugit.ui.screens.bugForm

import android.net.Uri
import java.io.Serializable

data class BugFormState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val imageUri: Uri? = null,
    val isSubmitted: Boolean = false
) : Serializable
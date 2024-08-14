package com.hu.bugit.ui.screens.bugForm

import android.net.Uri

sealed interface BugFormIntent {
    data class OnTitleChanged(val title: String) : BugFormIntent
    data class OnDescriptionChanged(val description: String) : BugFormIntent
    data class OnImageChanged(val imageUri: Uri?) : BugFormIntent
    data object Submit : BugFormIntent
}
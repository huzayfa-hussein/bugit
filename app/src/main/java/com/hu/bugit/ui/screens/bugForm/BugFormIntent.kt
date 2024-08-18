package com.hu.bugit.ui.screens.bugForm

import android.net.Uri


/**
 * Represents the possible intents that can be sent to the BugFormViewModel.
 */
sealed interface BugFormIntent {
    data class OnTitleChanged(val title: String) : BugFormIntent
    data class OnDescriptionChanged(val description: String) : BugFormIntent
    data class OnImageChanged(val imageUri: Uri?, val imagePath: String) : BugFormIntent
    data object Submit : BugFormIntent
    data class OnDismissDialog(val success: Boolean) : BugFormIntent
}
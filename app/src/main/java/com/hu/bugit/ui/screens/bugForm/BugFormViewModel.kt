package com.hu.bugit.ui.screens.bugForm

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BugFormViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(BugFormState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    fun onIntent(intent: BugFormIntent) {
        when (intent) {
            is BugFormIntent.OnTitleChanged -> {
                _uiState.update { it.copy(title = intent.title) }
            }

            is BugFormIntent.OnDescriptionChanged -> {
                _uiState.update { it.copy(description = intent.description) }
            }

            is BugFormIntent.OnImageChanged -> {
                _uiState.update { it.copy(imageUri = intent.imageUri) }
            }

            is BugFormIntent.Submit -> {
                submitBug()
            }
        }
    }

    private fun submitBug() {
        TODO("Not yet implemented")
    }

    fun updateImageUri(imageUri: Uri?) {
        _uiState.update { it.copy(imageUri = imageUri) }
    }
}
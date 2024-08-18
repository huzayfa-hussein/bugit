package com.hu.bugit.ui.screens.bugForm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.usecase.bugForm.SubmitBugUseCase
import com.hu.bugit.domain.usecase.bugForm.UploadImageUseCase
import com.hu.bugit.extensions.handleUseCase
import com.hu.bugit.extensions.onError
import com.hu.bugit.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BugFormViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val submitBugUseCase: SubmitBugUseCase
) : ViewModel() {

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
                _uiState.update {
                    it.copy(
                        imageUri = intent.imageUri,
                        imageFilePath = intent.imagePath
                    )
                }
            }

            is BugFormIntent.Submit -> {
                if (_uiState.value.title.isEmpty() || _uiState.value.description.isEmpty() || _uiState.value.imageUri == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showDialog = true,
                            dialogMessage = "You have to add title, description and image in order to submit a new Bug. Please try again"
                        )
                    }
                } else {
                    submitBug()
                }
            }

            is BugFormIntent.OnDismissDialog -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        showDialog = false,
                        dialogMessage = ""
                    )
                }
            }
        }
    }

    private fun submitBug() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            handleUseCase(
                uploadImageUseCase(_uiState.value.imageFilePath)
            ) {
                it.onSuccess { model ->
                    _uiState.update { state -> state.copy(imageUrl = model.data ?: "") }
                    handleUseCase(
                        submitBugUseCase(
                            title = _uiState.value.title,
                            description = _uiState.value.description,
                            imageUrl = _uiState.value.imageUrl,
                            platform = BugPlatform.NOTION
                        )
                    ) { resource ->
                        resource.onSuccess {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    isSubmitted = true,
                                    showDialog = true,
                                    dialogMessage = "You have successfully reported a new bug on Notion, you can view your bug list from the home screen."
                                )
                            }
                        }.onError { message, _ ->
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = message ?: "",
                                    showDialog = true,
                                    dialogMessage = "Failed to report a new bug on Notion, please try again later!"
                                )
                            }
                        }
                    }
                }.onError { message, _ ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = message ?: ""
                        )
                    }
                }
            }
        }
    }

    fun updateImageUri(imageUri: Uri?, imageFilePath: String) {
        _uiState.update { it.copy(imageUri = imageUri, imageFilePath = imageFilePath) }
    }
}
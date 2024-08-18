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

/**
 * Responsible for managing the state and logic of the bug form screen.
 *
 * This ViewModel handles user inputs related to the bug form, such as title, description, and image.
 * It also manages the process of submitting a bug report, including uploading an image and submitting
 * the report to a specific platform (e.g., Notion). The state of the form is represented by the `BugFormState`
 * and is exposed via a `StateFlow`.
 *
 * @property uploadImageUseCase The use case responsible for uploading the bug image.
 * @property submitBugUseCase The use case responsible for submitting the bug report.
 */
@HiltViewModel
class BugFormViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val submitBugUseCase: SubmitBugUseCase
) : ViewModel() {

    // The UI state of the bug form, exposed as a StateFlow for observing UI updates.
    private val _uiState = MutableStateFlow(BugFormState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    /**
     * Handles different user intents related to the bug form.
     *
     * Depending on the type of `BugFormIntent`, this function updates the UI state accordingly.
     * - `OnTitleChanged`: Updates the bug title in the UI state.
     * - `OnDescriptionChanged`: Updates the bug description in the UI state.
     * - `OnImageChanged`: Updates the selected image URI and file path in the UI state.
     * - `Submit`: Validates the form and initiates the submission process if valid.
     * - `OnDismissDialog`: Dismisses any visible dialog and resets dialog-related state.
     *
     * @param intent The intent representing a user action in the bug form.
     */
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

    /**
     * Submits the bug report by first uploading the image and then submitting the bug details.
     *
     * This function manages the process of uploading the selected image using `uploadImageUseCase`
     * and then submitting the bug details using `submitBugUseCase`. It updates the UI state to reflect
     * the progress and result of the submission.
     */
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

    /**
     * Updates the image URI and file path in the UI state.
     *
     * This function is used to set or update the image URI and corresponding file path in the
     * `BugFormState`, allowing the user to select a different image for the bug report.
     *
     * @param imageUri The URI of the selected image.
     * @param imageFilePath The file path of the selected image.
     */
    fun updateImageUri(imageUri: Uri?, imageFilePath: String) {
        _uiState.update { it.copy(imageUri = imageUri, imageFilePath = imageFilePath) }
    }
}
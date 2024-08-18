package com.hu.bugit.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.bugit.domain.usecase.home.BugListUseCase
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
 * Responsible for managing the state and logic of the home screen.
 *
 * @property bugListUseCase The use case responsible for fetching bug list.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bugListUseCase: BugListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    init {
        fetchBugList()
    }


    /**
     * Handles various intents related to the Home screen.
     *
     * @param intent The intent to be processed, which determines the action to be performed.
     *
     * The following intents are handled:
     * - `HomeIntent.OnRefreshBugList`: Triggers the refresh of the bug list by calling `fetchBugList()`.
     * - `HomeIntent.OnImageReceived`: Updates the UI state to indicate that an image has been received.
     *
     * Any other intents will be ignored.
     */
    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnRefreshBugList -> {
                fetchBugList()
            }

            is HomeIntent.OnImageReceived -> {
                _uiState.update { state ->
                    state.copy(isImageReceived = true)
                }
            }

            else -> {}
        }
    }

    /**
     * Fetches the list of bugs by invoking the [bugListUseCase] and updates the UI state accordingly.
     *
     * On success, the UI state is updated with the retrieved list of bugs. If the operation fails,
     * the UI state is updated to indicate an error, displaying the provided error message.
     */
    private fun fetchBugList() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            handleUseCase(
                bugListUseCase()
            ) {
                it.onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            bugList = it.data ?: listOf(),
                            isLoading = false
                        )
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
}
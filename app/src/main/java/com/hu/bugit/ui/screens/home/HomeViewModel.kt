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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bugListUseCase: BugListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    init {
        fetchBugList()
    }


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
package com.hu.bugit.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(isLoading = false))
    val uiState = _uiState.asStateFlow()


    fun onIntent(intent: HomeIntent) {
        when (intent) {
            else -> {}
        }
    }
}
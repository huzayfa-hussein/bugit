package com.hu.bugit.ui.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            else -> {}
        }
    }
}
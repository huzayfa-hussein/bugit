package com.hu.bugit.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.settings.PlatformSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.OnPlatformSelected -> {
                _uiState.update {
                    it.copy(selectedPlatform = intent.platform)
                }
            }

            else -> {}
        }
    }

    init {
        getPlatformList()
    }

    private fun getPlatformList() {
        // todo as future enhancements fetch platform list from API or local DB
        // Change platform reporting based on the selected platform item
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    platformList = listOf(
                        PlatformSetting(
                            platform = BugPlatform.NOTION,
                            isSelected = true
                        ),
                        PlatformSetting(
                            platform = BugPlatform.GOOGLE_SHEET,
                            isSelected = false
                        ),
                        PlatformSetting(
                            platform = BugPlatform.JIRA,
                            isSelected = false
                        ),
                        PlatformSetting(
                            platform = BugPlatform.TRELLO,
                            isSelected = false
                        ),

                        )
                )
            }

        }
    }
}
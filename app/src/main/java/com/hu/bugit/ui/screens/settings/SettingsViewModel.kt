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

/**
 * Responsible for managing the state and logic of the settings screen.
 *
 * This ViewModel handles user actions related to selecting a reporting platform and maintains
 * the state of available platforms. It provides functionality to update the selected platform and
 * fetch the list of available platforms, with future enhancements planned for integrating with an API
 * or local database.
 *
 * @constructor Creates an instance of `SettingsViewModel`. Dependency injection is handled by Hilt.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    // The UI state of the settings screen, exposed as a StateFlow for observing UI updates.
    private val _uiState = MutableStateFlow(SettingsState(isLoading = false))
    val uiState = _uiState.asStateFlow()

    /**
     * Handles user intents related to the settings screen.
     *
     * This function updates the UI state based on the type of `SettingsIntent` received.
     * - `OnPlatformSelected`: Updates the selected platform in the UI state.
     *
     * @param intent The intent representing a user action in the settings screen.
     */
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

    /**
     * Fetches the list of available platforms and updates the UI state.
     *
     * This function simulates fetching the platform list and sets default values for each platform.
     * It is intended to be enhanced in the future to fetch the platform list from an API or local database.
     */
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
package com.hu.bugit.ui.screens.settings

import com.hu.bugit.domain.models.BugPlatform

/**
 * Represents the possible intents that can be sent to the SettingsViewModel.
 */
sealed interface SettingsIntent {

    data class OnPlatformSelected(val platform: BugPlatform) : SettingsIntent
}
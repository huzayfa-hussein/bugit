package com.hu.bugit.ui.screens.settings

import com.hu.bugit.domain.models.BugPlatform

sealed interface SettingsIntent {

    data class OnPlatformSelected(val platform: BugPlatform) : SettingsIntent
}
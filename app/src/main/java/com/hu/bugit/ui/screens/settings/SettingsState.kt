package com.hu.bugit.ui.screens.settings

import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.settings.PlatformSetting
import java.io.Serializable

data class SettingsState(
    val isLoading: Boolean = false,
    val platformList: List<PlatformSetting> = listOf(),
    val selectedPlatform: BugPlatform = BugPlatform.NOTION
) : Serializable

package com.hu.bugit.domain.models.settings

import com.hu.bugit.domain.models.BugPlatform
import java.io.Serializable

data class PlatformSetting(
    val platform: BugPlatform,
    val isSelected: Boolean = false
) : Serializable

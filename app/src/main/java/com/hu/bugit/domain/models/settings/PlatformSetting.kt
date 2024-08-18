package com.hu.bugit.domain.models.settings

import com.hu.bugit.domain.models.BugPlatform
import java.io.Serializable

/**
 * Represents a setting for a specific bug platform.
 * @property platform The platform (e.g., BugPlatform.NOTION).
 * @property isSelected Indicates whether the setting is selected or not.
 */
data class PlatformSetting(
    val platform: BugPlatform,
    val isSelected: Boolean = false
) : Serializable

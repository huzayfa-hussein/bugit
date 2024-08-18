package com.hu.bugit.data.persistence

import androidx.room.TypeConverter
import com.hu.bugit.domain.models.BugPlatform

class Converters {

    /**
     * Convert BugPlatform to String
     */
    @TypeConverter
    fun fromBugPlatform(platform: BugPlatform): String {
        return platform.name
    }

    /**
     * Convert String to BugPlatform
     */
    @TypeConverter
    fun toBugPlatform(platformString: String): BugPlatform {
        return BugPlatform.valueOf(platformString)
    }
}
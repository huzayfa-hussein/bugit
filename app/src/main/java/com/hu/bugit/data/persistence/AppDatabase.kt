package com.hu.bugit.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hu.bugit.data.persistence.entity.BugData

/**
 * Represents the Room database for the application.
 */
@Database(entities = [BugData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the bug data access object (DAO).
     * @return The bug DAO.
     */
    abstract fun bugDao(): BugDao
}
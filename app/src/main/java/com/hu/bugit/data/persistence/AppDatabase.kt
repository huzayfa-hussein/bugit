package com.hu.bugit.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hu.bugit.data.persistence.entity.BugData

@Database(entities = [BugData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bugDao(): BugDao
}
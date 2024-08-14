package com.hu.bugit.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hu.bugit.data.persistence.entity.BugData

@Dao
interface BugDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewBug(bugData: BugData): Long

    @Query("select * from bugs order by createdAt ASC")
    suspend fun getAllBugs(): List<BugData>

}
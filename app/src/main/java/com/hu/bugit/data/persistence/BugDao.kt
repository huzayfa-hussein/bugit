package com.hu.bugit.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hu.bugit.data.persistence.entity.BugData

/**
 * Represents the data access object (DAO) for the BugData entity.
 */
@Dao
interface BugDao {

    /**
     * Inserts a new bug data into the database.
     * @param bugData The bug data to be inserted.
     * @return The ID of the inserted row.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewBug(bugData: BugData): Long

    /**
     * Retrieves all bug data from the database.
     * @return A list of bug data.
     */
    @Query("select * from bugs order by id")
    suspend fun getAllBugs(): List<BugData>

}
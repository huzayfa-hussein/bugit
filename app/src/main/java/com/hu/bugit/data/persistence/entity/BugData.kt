package com.hu.bugit.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hu.bugit.domain.models.BugPlatform
import java.io.Serializable

@Entity(tableName = "bugs")
data class BugData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String,
    val imageUrl: String,
    val platform: BugPlatform,
    val createdAt: String,
    val url: String
) : Serializable

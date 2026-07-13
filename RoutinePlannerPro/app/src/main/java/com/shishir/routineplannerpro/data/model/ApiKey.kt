package com.shishir.routineplannerpro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "api_keys")
data class ApiKey(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val key: String,
    val createdAt: Long = System.currentTimeMillis()
)

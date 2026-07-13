package com.shishir.routineplannerpro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: RoutineType,
    val createdAt: Long = System.currentTimeMillis(),
    val isDefault: Boolean = false // Daily and Class routines are default
)

enum class RoutineType {
    DAILY,
    CLASS,
    CUSTOM
}

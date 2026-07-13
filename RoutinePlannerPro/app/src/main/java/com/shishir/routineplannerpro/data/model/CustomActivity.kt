package com.shishir.routineplannerpro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "custom_activities")
data class CustomActivity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val routineId: String,
    val name: String,
    val startTime: String? = null, // Optional for custom routines
    val endTime: String? = null,   // Optional for custom routines
    val startDate: String? = null, // Format: "dd/MM/yyyy"
    val endDate: String? = null,   // Format: "dd/MM/yyyy"
    val specificDate: String? = null, // Format: "dd/MM/yyyy"
    val weekDays: List<String> = emptyList(),
    val extraFields: Map<String, String> = emptyMap(), // For additional info like Room No, etc.
    val reminderEnabled: Boolean = false,
    val reminderTime: Int = 5,
    val alarmEnabled: Boolean = false,
    val alarmTime: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

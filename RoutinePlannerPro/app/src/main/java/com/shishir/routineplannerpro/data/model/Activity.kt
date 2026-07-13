package com.shishir.routineplannerpro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val routineId: String,
    val name: String,
    val startTime: String, // Format: "HH:mm" or "HH:mm a"
    val endTime: String,   // Format: "HH:mm" or "HH:mm a"
    val startDate: String? = null, // Format: "dd/MM/yyyy"
    val endDate: String? = null,   // Format: "dd/MM/yyyy"
    val specificDate: String? = null, // Format: "dd/MM/yyyy"
    val weekDays: List<String> = emptyList(), // ["Monday", "Tuesday", ...] or empty for every day
    val reminderEnabled: Boolean = false,
    val reminderTime: Int = 5, // Minutes before
    val alarmEnabled: Boolean = false,
    val alarmTime: Int = 0, // Minutes before (0 means at start time)
    val extraFields: Map<String, String> = emptyMap(), // For additional info like Room No, Teacher, etc.
    val createdAt: Long = System.currentTimeMillis()
)

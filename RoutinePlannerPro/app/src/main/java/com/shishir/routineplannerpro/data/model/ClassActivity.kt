package com.shishir.routineplannerpro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "class_activities")
data class ClassActivity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val routineId: String,
    val courseName: String,
    val roomNo: String,
    val startTime: String, // Format: "HH:mm" or "HH:mm a"
    val endTime: String,   // Format: "HH:mm" or "HH:mm a"
    val days: List<String>, // ["Sat", "Sun", ...]
    val classType: String, // "Theory" or "Lab"
    val teacherName: String? = null,
    val section: String? = null,
    val startDate: String? = null, // Format: "dd/MM/yyyy"
    val endDate: String? = null,   // Format: "dd/MM/yyyy"
    val specificDate: String? = null, // Format: "dd/MM/yyyy"
    val reminderEnabled: Boolean = false,
    val reminderTime: Int = 5, // Minutes before
    val alarmEnabled: Boolean = false,
    val alarmTime: Int = 0, // Minutes before
    val createdAt: Long = System.currentTimeMillis()
)

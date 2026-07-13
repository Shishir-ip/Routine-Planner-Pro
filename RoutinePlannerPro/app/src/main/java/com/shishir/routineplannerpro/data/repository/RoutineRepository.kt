package com.shishir.routineplannerpro.data.repository

import android.content.Context
import com.shishir.routineplannerpro.data.local.AppDatabase
import com.shishir.routineplannerpro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class RoutineRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val routineDao = database.routineDao()
    private val activityDao = database.activityDao()
    private val classActivityDao = database.classActivityDao()
    private val customActivityDao = database.customActivityDao()
    private val apiKeyDao = database.apiKeyDao()

    // Routine operations
    val allRoutines: Flow<List<Routine>> = routineDao.getAllRoutines()

    suspend fun getRoutineById(id: String): Routine? = routineDao.getRoutineById(id)
    suspend fun insertRoutine(routine: Routine) = routineDao.insertRoutine(routine)
    suspend fun deleteRoutine(routine: Routine) = routineDao.deleteRoutine(routine)
    suspend fun getCustomRoutines(): List<Routine> = routineDao.getCustomRoutines()

    // Activity operations
    fun getActivitiesByRoutineId(routineId: String): Flow<List<Activity>> = 
        activityDao.getActivitiesByRoutineId(routineId)
    
    fun getAllActivities(): Flow<List<Activity>> = activityDao.getAllActivities()
    suspend fun insertActivity(activity: Activity) = activityDao.insertActivity(activity)
    suspend fun deleteActivity(activity: Activity) = activityDao.deleteActivity(activity)
    suspend fun updateActivity(activity: Activity) = activityDao.updateActivity(activity)

    // Class Activity operations
    fun getClassActivitiesByRoutineId(routineId: String): Flow<List<ClassActivity>> = 
        classActivityDao.getClassActivitiesByRoutineId(routineId)
    
    fun getAllClassActivities(): Flow<List<ClassActivity>> = classActivityDao.getAllClassActivities()
    suspend fun insertClassActivity(activity: ClassActivity) = classActivityDao.insertClassActivity(activity)
    suspend fun deleteClassActivity(activity: ClassActivity) = classActivityDao.deleteClassActivity(activity)
    suspend fun updateClassActivity(activity: ClassActivity) = classActivityDao.updateClassActivity(activity)

    // Custom Activity operations
    fun getCustomActivitiesByRoutineId(routineId: String): Flow<List<CustomActivity>> = 
        customActivityDao.getCustomActivitiesByRoutineId(routineId)
    
    suspend fun insertCustomActivity(activity: CustomActivity) = customActivityDao.insertCustomActivity(activity)
    suspend fun deleteCustomActivity(activity: CustomActivity) = customActivityDao.deleteCustomActivity(activity)
    suspend fun updateCustomActivity(activity: CustomActivity) = customActivityDao.updateCustomActivity(activity)

    // API Key operations
    fun getApiKey(): Flow<ApiKey?> = apiKeyDao.getApiKey()
    suspend fun insertApiKey(apiKey: ApiKey) = apiKeyDao.insertApiKey(apiKey)
    suspend fun deleteApiKey(apiKey: ApiKey) = apiKeyDao.deleteApiKey(apiKey)

    // Helper function to check if an activity is active on a given date
    fun isActivityActiveOnDate(activity: Activity, date: Date): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        
        // Check specific date
        activity.specificDate?.let {
            val specificDate = sdf.parse(it)
            if (specificDate != null) {
                return sdf.format(date) == it
            }
        }

        // Check date range
        activity.startDate?.let { startDate ->
            activity.endDate?.let { endDate ->
                val start = sdf.parse(startDate)
                val end = sdf.parse(endDate)
                if (start != null && end != null) {
                    if (date.before(start) || date.after(end)) {
                        return false
                    }
                }
            }
        }

        // Check weekday
        if (activity.weekDays.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val dayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
            if (dayName !in activity.weekDays) {
                return false
            }
        }

        return true
    }

    // Helper function to check if a class activity is active on a given date
    fun isClassActivityActiveOnDate(activity: ClassActivity, date: Date): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        
        // Check specific date
        activity.specificDate?.let {
            val specificDate = sdf.parse(it)
            if (specificDate != null) {
                return sdf.format(date) == it
            }
        }

        // Check date range
        activity.startDate?.let { startDate ->
            activity.endDate?.let { endDate ->
                val start = sdf.parse(startDate)
                val end = sdf.parse(endDate)
                if (start != null && end != null) {
                    if (date.before(start) || date.after(end)) {
                        return false
                    }
                }
            }
        }

        // Check day of week
        if (activity.days.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val dayIndex = calendar.get(Calendar.DAY_OF_WEEK)
            val dayMap = mapOf(
                Calendar.SATURDAY to "Sat",
                Calendar.SUNDAY to "Sun",
                Calendar.MONDAY to "Mon",
                Calendar.TUESDAY to "Tue",
                Calendar.WEDNESDAY to "Wed",
                Calendar.THURSDAY to "Thu",
                Calendar.FRIDAY to "Fri"
            )
            val currentDay = dayMap[dayIndex]
            if (currentDay !in activity.days) {
                return false
            }
        }

        return true
    }
}

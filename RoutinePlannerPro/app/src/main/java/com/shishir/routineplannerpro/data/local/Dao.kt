package com.shishir.routineplannerpro.data.local

import androidx.room.*
import com.shishir.routineplannerpro.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routines ORDER BY createdAt DESC")
    fun getAllRoutines(): Flow<List<Routine>>

    @Query("SELECT * FROM routines WHERE id = :id")
    suspend fun getRoutineById(id: String): Routine?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine)

    @Delete
    suspend fun deleteRoutine(routine: Routine)

    @Query("SELECT * FROM routines WHERE isDefault = 0")
    suspend fun getCustomRoutines(): List<Routine>
}

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities WHERE routineId = :routineId ORDER BY createdAt DESC")
    fun getActivitiesByRoutineId(routineId: String): Flow<List<Activity>>

    @Query("SELECT * FROM activities ORDER BY createdAt DESC")
    fun getAllActivities(): Flow<List<Activity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Delete
    suspend fun deleteActivity(activity: Activity)

    @Update
    suspend fun updateActivity(activity: Activity)
}

@Dao
interface ClassActivityDao {
    @Query("SELECT * FROM class_activities WHERE routineId = :routineId ORDER BY createdAt DESC")
    fun getClassActivitiesByRoutineId(routineId: String): Flow<List<ClassActivity>>

    @Query("SELECT * FROM class_activities ORDER BY createdAt DESC")
    fun getAllClassActivities(): Flow<List<ClassActivity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClassActivity(activity: ClassActivity)

    @Delete
    suspend fun deleteClassActivity(activity: ClassActivity)

    @Update
    suspend fun updateClassActivity(activity: ClassActivity)
}

@Dao
interface CustomActivityDao {
    @Query("SELECT * FROM custom_activities WHERE routineId = :routineId ORDER BY createdAt DESC")
    fun getCustomActivitiesByRoutineId(routineId: String): Flow<List<CustomActivity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomActivity(activity: CustomActivity)

    @Delete
    suspend fun deleteCustomActivity(activity: CustomActivity)

    @Update
    suspend fun updateCustomActivity(activity: CustomActivity)
}

@Dao
interface ApiKeyDao {
    @Query("SELECT * FROM api_keys LIMIT 1")
    fun getApiKey(): Flow<ApiKey?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApiKey(apiKey: ApiKey)

    @Delete
    suspend fun deleteApiKey(apiKey: ApiKey)
}

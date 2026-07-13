package com.shishir.routineplannerpro.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shishir.routineplannerpro.data.model.*

@Database(
    entities = [
        Routine::class,
        Activity::class,
        ClassActivity::class,
        CustomActivity::class,
        ApiKey::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun activityDao(): ActivityDao
    abstract fun classActivityDao(): ClassActivityDao
    abstract fun customActivityDao(): CustomActivityDao
    abstract fun apiKeyDao(): ApiKeyDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "routine_planner_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

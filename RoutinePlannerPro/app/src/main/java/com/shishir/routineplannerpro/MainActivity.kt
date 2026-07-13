package com.shishir.routineplannerpro

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.shishir.routineplannerpro.data.model.Routine
import com.shishir.routineplannerpro.data.model.RoutineType
import com.shishir.routineplannerpro.data.repository.RoutineRepository
import com.shishir.routineplannerpro.ui.navigation.Screen
import com.shishir.routineplannerpro.ui.screens.HomeScreen
import com.shishir.routineplannerpro.ui.theme.RoutinePlannerProTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private lateinit var repository: RoutineRepository
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            // Handle permission result if needed
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        repository = RoutineRepository(applicationContext)
        
        // Request necessary permissions
        requestPermissions()
        
        // Initialize default routines if they don't exist
        initializeDefaultRoutines()
        
        setContent {
            RoutinePlannerProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        onNavigateToSettings = { /* Navigate to settings */ },
                        onNavigateToAddRoutine = { /* Navigate to add routine */ }
                    )
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.SCHEDULE_EXACT_ALARM)
        }
        
        requestPermissionLauncher.launch(
            permissions.toTypedArray()
        )
    }

    private fun initializeDefaultRoutines() {
        CoroutineScope(Dispatchers.IO).launch {
            // Check if default routines exist
            val existingRoutines = repository.allRoutines
            // This would need to be handled properly with Flow
            // For now, we'll just create them on first run
            
            val dailyRoutine = Routine(
                name = "Daily Routine",
                type = RoutineType.DAILY,
                isDefault = true
            )
            
            val classRoutine = Routine(
                name = "Class Routine",
                type = RoutineType.CLASS,
                isDefault = true
            )
            
            repository.insertRoutine(dailyRoutine)
            repository.insertRoutine(classRoutine)
        }
    }
}

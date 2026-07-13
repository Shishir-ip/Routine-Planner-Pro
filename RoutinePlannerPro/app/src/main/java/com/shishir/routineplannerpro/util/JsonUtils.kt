package com.shishir.routineplannerpro.util

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

object JsonImporterExporter {
    
    fun exportRoutineToJson(context: Context, routineData: Map<String, Any>): String {
        return com.google.gson.GsonBuilder().setPrettyPrinting().toJson(routineData)
    }
    
    suspend fun importRoutineFromJson(context: Context, jsonContent: String): Result<Map<String, Any>> {
        return try {
            val jsonObject = com.google.gson.JsonParser.parseString(jsonContent).asJsonObject
            val result = mutableMapOf<String, Any>()
            
            // Parse activities if present
            if (jsonObject.has("activities")) {
                result["activities"] = jsonObject.getAsJsonArray("activities")
            }
            
            // Parse class activities if present
            if (jsonObject.has("classActivities")) {
                result["classActivities"] = jsonObject.getAsJsonArray("classActivities")
            }
            
            // Parse custom activities if present
            if (jsonObject.has("customActivities")) {
                result["customActivities"] = jsonObject.getAsJsonArray("customActivities")
            }
            
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun parseTime(timeString: String): Date? {
        return try {
            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(timeString)
        } catch (e: Exception) {
            null
        }
    }
    
    fun parseDate(dateString: String): Date? {
        return try {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}

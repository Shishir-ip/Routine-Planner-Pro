package com.shishir.routineplannerpro.util

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class OpenRouterRequest(
    val model: String = "openrouter/free",
    val messages: List<Message>,
    val max_tokens: Int = 1000
)

data class Message(
    val role: String,
    val content: String
)

data class OpenRouterResponse(
    val choices: List<Choice>?
)

data class Choice(
    val message: Message?
)

class OpenRouterApi(private val apiKey: String) {

    suspend fun generateRoutineJson(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://openrouter.ai/api/v1/chat/completions")
            val connection = url.openConnection() as HttpURLConnection
            
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Authorization", "Bearer $apiKey")
            connection.doOutput = true
            
            val request = OpenRouterRequest(
                model = "openrouter/free",
                messages = listOf(
                    Message(
                        role = "system",
                        content = """
You are a JSON generator for a routine planner app. 
Given a natural language description of activities, generate a valid JSON output that can be imported into the app.

The JSON structure should follow this format:
{
    "activities": [
        {
            "name": "Activity Name",
            "startTime": "HH:mm",
            "endTime": "HH:mm",
            "weekDays": ["Monday", "Tuesday"], // or empty array for every day
            "startDate": "dd/MM/yyyy", // optional
            "endDate": "dd/MM/yyyy", // optional
            "specificDate": "dd/MM/yyyy", // optional, overrides date range
            "reminderEnabled": true/false,
            "reminderTime": 5, // minutes before
            "alarmEnabled": true/false,
            "alarmTime": 0, // minutes before
            "extraFields": {"key": "value"} // optional additional info
        }
    ],
    "classActivities": [
        {
            "courseName": "Course Name",
            "roomNo": "001",
            "startTime": "HH:mm",
            "endTime": "HH:mm",
            "days": ["Sat", "Sun"],
            "classType": "Theory", // or "Lab"
            "teacherName": "Teacher Name", // optional
            "section": "A", // optional
            "startDate": "dd/MM/yyyy", // optional
            "endDate": "dd/MM/yyyy", // optional
            "specificDate": "dd/MM/yyyy", // optional
            "reminderEnabled": true/false,
            "reminderTime": 5,
            "alarmEnabled": true/false,
            "alarmTime": 0
        }
    ]
}

Only return the JSON. Do not include any explanation or markdown formatting.
""".trimIndent()
                    ),
                    Message(
                        role = "user",
                        content = prompt
                    )
                )
            )
            
            val requestBody = com.google.gson.Gson().toJson(request)
            
            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(requestBody)
                writer.flush()
            }
            
            val responseCode = connection.responseCode
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    reader.readText()
                }
                
                val jsonResponse = com.google.gson.Gson().fromJson(response, OpenRouterResponse::class.java)
                val generatedContent = jsonResponse.choices?.firstOrNull()?.message?.content 
                    ?: return@withContext Result.failure(Exception("No response from API"))
                
                // Extract JSON from the response (in case it's wrapped in markdown)
                val jsonContent = extractJsonFromResponse(generatedContent)
                Result.success(jsonContent)
            } else {
                val errorResponse = BufferedReader(InputStreamReader(connection.errorStream)).use { reader ->
                    reader.readText()
                }
                Result.failure(Exception("API Error: $errorResponse"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun extractJsonFromResponse(response: String): String {
        // Remove markdown code blocks if present
        val jsonPattern = Regex("\\{[\\s\\S]*\\}")
        val match = jsonPattern.find(response)
        return match?.value ?: response.trim()
    }
}

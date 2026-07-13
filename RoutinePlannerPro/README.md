# Routine Planner Pro - Android App

A professional routine planning Android application built with Kotlin and Jetpack Compose.

## Features

### Core Functionality
- **Daily Routine**: Manage your daily activities with customizable time ranges, reminders, and alarms
- **Class Routine**: Organize your class schedule with course details, room numbers, and teacher information
- **Custom Routines**: Create additional routine types (e.g., Exam Routine) as needed
- **Unified Daily View**: All activities from different routines appear in the daily view for easy reference

### Activity Management
- Add activity name and time range (e.g., 12:00 AM to 4:00 PM)
- Optional reminder with customizable time before activity starts (default: 5 minutes)
- Optional alarm that works even in silent mode
- Date range support (start date to end date) or single specific date
- Weekday selection (specific days or every day)
- Expandable extra fields for additional information

### Class Routine Features
- Course name, room number, and class type (Theory/Lab)
- Teacher name and section (optional)
- Day selection (Sat, Sun, Mon, etc.)
- Time range for each class
- Expandable detail view with smooth animations

### Settings
- **Theme Toggle**: Switch between Day and Night modes
- **Import/Export**: Export routines to JSON format or import from JSON files
- **AI Generator**: Generate routine JSON using natural language input
  - Uses OpenRouter API with `openrouter/free` model
  - Example: "want to add Studying from 10 am to 12:30 pm on sunday, tuesday..."
  - Securely store and manage API keys (displayed as *************)
- **Developer Info**: 
  - Name: Shishir
  - GitHub: https://github.com/Shishir-ip

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Database**: Room Persistence Library
- **Navigation**: Navigation Compose
- **Dependency Injection**: Manual DI (can be extended with Hilt/Koin)
- **JSON Processing**: Gson
- **API Integration**: OpenRouter API for AI features

## Project Structure

```
app/src/main/java/com/shishir/routineplannerpro/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   └── Dao.kt
│   ├── model/
│   │   ├── Routine.kt
│   │   ├── Activity.kt
│   │   ├── ClassActivity.kt
│   │   ├── CustomActivity.kt
│   │   └── ApiKey.kt
│   └── repository/
│       └── RoutineRepository.kt
├── ui/
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   ├── screens/
│   │   └── HomeScreen.kt
│   ├── components/
│   └── navigation/
│       └── Navigation.kt
├── util/
│   ├── AlarmScheduler.kt
│   ├── NotificationUtils.kt
│   ├── OpenRouterApi.kt
│   └── JsonUtils.kt
└── MainActivity.kt
```

## Permissions Required

- `POST_NOTIFICATIONS`: For sending reminders and alarms
- `SCHEDULE_EXACT_ALARM`: For scheduling precise alarm times
- `RECEIVE_BOOT_COMPLETED`: To reschedule alarms after device restart
- `VIBRATE`: For vibration alerts
- `WAKE_LOCK`: To wake device for alarms
- Storage permissions for import/export functionality

## Build Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34

### Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd RoutinePlannerPro
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the `RoutinePlannerPro` folder

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle files
   - Wait for dependencies to download

4. **Build and Run**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon)
   - Or use: `./gradlew installDebug`

### Command Line Build

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

## Usage Guide

### Adding a Daily Activity
1. Tap the "+" FAB button
2. Select "Daily Routine"
3. Enter activity name
4. Set time range (start and end time)
5. Optionally enable reminder/alarm and set time before
6. Set date range or specific date
7. Select weekdays or choose "Every day"
8. Tap "+" to add extra fields if needed
9. Save

### Adding a Class
1. Switch to "Class Routine" tab
2. Tap the "+" FAB button
3. Enter course name and room number
4. Select days of the week
5. Set class time
6. Choose class type (Theory/Lab)
7. Optionally add teacher name and section
8. Set date range if applicable
9. Save

### Using AI Generator
1. Go to Settings
2. Add your OpenRouter API key (will be saved securely)
3. Navigate to AI Generator
4. Enter natural language description like:
   > "want to add Studying from 10 am to 12:30 pm on sunday, tuesday daily routine, 9pm to 10 pm is walking"
5. Generate JSON
6. Import the generated JSON

### Import/Export
1. Go to Settings
2. Select "Import/Export"
3. Choose a routine to export
4. Save JSON file or import from existing file

## Future Enhancements

- [ ] Widget support for home screen
- [ ] Calendar integration
- [ ] Cloud backup and sync
- [ ] Sharing routines with friends
- [ ] Statistics and insights
- [ ] Multiple alarm tones
- [ ] Recurring patterns (weekly, monthly, etc.)
- [ ] Voice input for activities

## Developer

**Shishir**  
GitHub: [https://github.com/Shishir-ip](https://github.com/Shishir-ip)

## License

This project is open source and available under the MIT License.

## Support

For issues, feature requests, or contributions, please visit the GitHub repository.

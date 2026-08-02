# MorseMaster 📡

A **Duolingo-style Morse code learning app** for Android, built with Kotlin + Jetpack Compose.

## Features
- 🎓 8 structured lessons (A–Z vollständig + Zahlen 0–9)
- ✅ Multiple-choice quiz with instant ✅/❌ feedback
- 📊 Progress bar per lesson
- ⚡ XP system: earn 10 XP per correct answer, +20 bonus for perfect lessons
- 🔥 Daily streak tracker
- 🏆 Level system (every 100 XP = 1 level)
- 📳 Haptic feedback (correct = short buzz, wrong = double buzz, perfect = celebration pattern)
- 🌙 Dark/Light theme support
- 💾 Progress persisted via SharedPreferences

## Project Structure
```
app/src/main/java/com/morsemaster/app/
├── MainActivity.kt
├── navigation/
│   └── AppNavigation.kt
├── data/
│   ├── MorseCode.kt       # Full A–Z, 0–9 Morse alphabet + encode/decode
│   ├── Lesson.kt          # Lesson & Exercise data + LessonRepository (8 lessons)
│   └── UserProgress.kt    # XP, streak, level, completed lessons (SharedPrefs)
├── util/
│   └── HapticFeedback.kt  # correct / wrong / celebrate vibration patterns
└── ui/
    ├── screen/
    │   ├── HomeScreen.kt   # Lesson list + XP bar + streak display
    │   ├── LessonScreen.kt # Quiz with haptic feedback + animated feedback label
    │   └── ResultScreen.kt # Score + XP earned + level + streak
    └── theme/
        └── Theme.kt
```

## Getting Started
1. Clone the repository
2. Open in Android Studio (Ladybug or newer)
3. Run on emulator or device (min SDK 26)

## Roadmap
- [x] Sound/vibration feedback for correct/wrong answers
- [x] XP system with level progression
- [x] Daily streak tracker
- [x] Complete A–Z + 0–9 lessons (8 total)
- [ ] Morse keyboard input mode (tap . and - directly)
- [ ] Spaced repetition for weak letters
- [ ] Leaderboard & achievements
- [ ] Audio playback of Morse signals

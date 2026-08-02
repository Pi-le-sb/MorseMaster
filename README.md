# MorseMaster 📡

A **Duolingo-style Morse code learning app** for Android, built with Kotlin + Jetpack Compose.

## Features
- 🎓 Structured lessons (Letter → Morse, Morse → Letter)
- ✅ Multiple-choice quiz format with instant feedback
- 📊 Progress bar per lesson
- 🏆 Result screen with score and emoji feedback
- 🌙 Dark/Light theme support

## Project Structure
```
app/src/main/java/com/morsemaster/app/
├── MainActivity.kt
├── navigation/
│   └── AppNavigation.kt
├── data/
│   ├── MorseCode.kt       # Full A–Z, 0–9 Morse alphabet + encode/decode
│   └── Lesson.kt          # Lesson & Exercise data classes + LessonRepository
└── ui/
    ├── screen/
    │   ├── HomeScreen.kt
    │   ├── LessonScreen.kt
    │   └── ResultScreen.kt
    └── theme/
        └── Theme.kt
```

## Getting Started
1. Clone the repository
2. Open in Android Studio (Ladybug or newer)
3. Run on emulator or device (min SDK 26)

## Roadmap
- [ ] Sound/vibration feedback for dit/dah
- [ ] Streak & XP system (Duolingo-style)
- [ ] Morse keyboard input mode
- [ ] Spaced repetition for weak letters
- [ ] Leaderboard & achievements

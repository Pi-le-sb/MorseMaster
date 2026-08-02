# MorseMaster 📡

A **Duolingo-style Morse code learning app** for Android, built with Kotlin + Jetpack Compose.

## Features

### 🎓 Lernen
- 8 strukturierte Lektionen (A–Z vollständig + Zahlen 0–9)
- Multiple-Choice-Quiz mit sofortigem ✅/❌ Feedback
- Fortschrittsbalken pro Lektion

### ⚡ Gamification
- **XP-System**: 10 XP pro richtige Antwort, +20 Bonus für perfekte Lektionen
- **Level-System**: alle 100 XP = 1 Level (mit Fortschrittsbalken)
- **Daily Streak**: täglicher Streak-Tracker
- **9 Achievements**: Erster Schritt, Perfektionist, Streak-Krieger, Morse-Experte u.v.m.

### 📳 Morse-Tastatur
- Direkte Eingabe von · (Punkt) und — (Strich)
- Echtzeit-Dekodierung der eingegebenen Morse-Signale
- Text als Morse-Signal abspielen

### 🔄 Spaced Repetition
- Verfolgt schwache Buchstaben automatisch
- Dedizierter Wiederholungs-Modus für schwache Stellen

### 🔊 Audio
- Echte Morse-Töne (700 Hz) via `AudioTrack`
- Abspiel-Button bei Morse→Buchstabe Fragen
- Audio-Wiedergabe in der Morse-Tastatur

### ⚙️ Einstellungen
- Haptisches Feedback ein/aus
- Audio ein/aus

## Projekt-Struktur
```
app/src/main/java/com/morsemaster/app/
├── MainActivity.kt
├── navigation/
│   └── AppNavigation.kt        # Home, Lesson, Result, Keyboard, Review, Achievements, Settings
├── data/
│   ├── MorseCode.kt            # A–Z, 0–9 + encode/decode
│   ├── Lesson.kt               # 8 Lektionen + LessonRepository
│   ├── UserProgress.kt         # XP, Streak, Level, SharedPrefs
│   ├── Achievements.kt         # 9 Achievements
│   ├── SpacedRepetition.kt     # Fehler-Tracking + Review-Generierung
│   └── UserSettings.kt         # Audio/Haptics Toggle
├── util/
│   ├── HapticFeedback.kt       # correct / wrong / celebrate
│   └── MorseAudioPlayer.kt     # 700 Hz PCM Morse-Töne via AudioTrack
└── ui/screen/
    ├── HomeScreen.kt           # Lektionsliste + XP + Streak + BottomNav
    ├── LessonScreen.kt         # Quiz + Haptics + Audio + SpacedRep
    ├── ResultScreen.kt         # Score + XP + Level + Streak
    ├── MorseKeyboardScreen.kt  # Freie Morse-Eingabe + Dekodierung + Audio
    ├── ReviewScreen.kt         # Wiederholung schwacher Buchstaben
    ├── AchievementsScreen.kt   # Achievement-Galerie
    └── SettingsScreen.kt       # Audio/Haptics-Einstellungen
```

## Getting Started
1. Repository klonen
2. In Android Studio (Ladybug oder neuer) öffnen
3. Auf Emulator oder Gerät starten (min SDK 26)

## Roadmap
- [x] Vibrations-/Haptic-Feedback
- [x] XP-System mit Level-Progression
- [x] Daily Streak
- [x] Vollständiges A–Z + 0–9 (8 Lektionen)
- [x] Morse-Tastatur (Tippen von · und —)
- [x] Spaced Repetition für schwache Buchstaben
- [x] Audio-Wiedergabe von Morse-Signalen (700 Hz PCM)
- [x] Achievements-System (9 Erfolge)
- [x] Einstellungen (Audio/Haptics)
- [ ] Offline-Widget für tägliche Lern-Erinnerung
- [ ] Export von gelerntem Morse-Text als Audiodatei

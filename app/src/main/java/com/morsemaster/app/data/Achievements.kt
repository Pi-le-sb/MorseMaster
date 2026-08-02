package com.morsemaster.app.data

import android.content.Context
import androidx.core.content.edit

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String
)

object AchievementRepository {
    val all = listOf(
        Achievement("first_lesson",   "Erster Schritt",      "Erste Lektion abgeschlossen",         "🎓"),
        Achievement("perfect_lesson", "Perfektionist",       "Eine Lektion ohne Fehler abgeschlossen","🌟"),
        Achievement("streak_3",       "3er Streak",          "3 Tage in Folge gelernt",             "🔥"),
        Achievement("streak_7",       "Wochenkrieger",       "7 Tage in Folge gelernt",             "⚡"),
        Achievement("xp_100",         "Lehrling",            "100 XP gesammelt",                    "🏅"),
        Achievement("xp_500",         "Fortgeschrittener",   "500 XP gesammelt",                    "🥈"),
        Achievement("xp_1000",        "Morse-Experte",       "1000 XP gesammelt",                   "🥇"),
        Achievement("all_lessons",    "Vollständiger Kurs",   "Alle Lektionen abgeschlossen",        "📡"),
        Achievement("review_done",    "Schwachstellen-Jäger","Eine Wiederholungslektion absolviert", "🔍")
    )

    fun checkAndUnlock(ctx: Context, xp: Int, streak: Int, completedCount: Int, perfect: Boolean, didReview: Boolean): List<Achievement> {
        val prefs = ctx.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        val newlyUnlocked = mutableListOf<Achievement>()
        fun maybeUnlock(a: Achievement, condition: Boolean) {
            if (condition && !prefs.getBoolean(a.id, false)) {
                prefs.edit { putBoolean(a.id, true) }
                newlyUnlocked.add(a)
            }
        }
        maybeUnlock(all[0], completedCount >= 1)
        maybeUnlock(all[1], perfect)
        maybeUnlock(all[2], streak >= 3)
        maybeUnlock(all[3], streak >= 7)
        maybeUnlock(all[4], xp >= 100)
        maybeUnlock(all[5], xp >= 500)
        maybeUnlock(all[6], xp >= 1000)
        maybeUnlock(all[7], completedCount >= LessonRepository.lessons.size)
        maybeUnlock(all[8], didReview)
        return newlyUnlocked
    }

    fun getUnlocked(ctx: Context): List<Achievement> {
        val prefs = ctx.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        return all.filter { prefs.getBoolean(it.id, false) }
    }
}

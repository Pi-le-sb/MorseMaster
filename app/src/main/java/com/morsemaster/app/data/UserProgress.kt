package com.morsemaster.app.data

import android.content.Context
import androidx.core.content.edit

object UserProgress {
    private const val PREFS = "morse_progress"
    private const val KEY_XP = "xp"
    private const val KEY_STREAK = "streak"
    private const val KEY_LAST_DATE = "last_date"
    private const val KEY_COMPLETED = "completed_lessons"

    fun getXp(ctx: Context): Int =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(KEY_XP, 0)

    fun getStreak(ctx: Context): Int =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(KEY_STREAK, 0)

    fun getCompletedLessons(ctx: Context): Set<Int> =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(KEY_COMPLETED, emptySet())!!
            .map { it.toInt() }.toSet()

    /** Call after finishing a lesson. Returns awarded XP. */
    fun recordLessonComplete(ctx: Context, lessonId: Int, correct: Int, total: Int): Int {
        val prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val xpEarned = calculateXp(correct, total)
        val today = java.time.LocalDate.now().toString()
        val lastDate = prefs.getString(KEY_LAST_DATE, "") ?: ""
        val yesterday = java.time.LocalDate.now().minusDays(1).toString()

        val newStreak = when (lastDate) {
            today -> prefs.getInt(KEY_STREAK, 1)          // already played today, keep streak
            yesterday -> prefs.getInt(KEY_STREAK, 0) + 1  // consecutive day
            else -> 1                                      // streak reset
        }

        val completed = prefs.getStringSet(KEY_COMPLETED, mutableSetOf())!!.toMutableSet()
        completed.add(lessonId.toString())

        prefs.edit {
            putInt(KEY_XP, prefs.getInt(KEY_XP, 0) + xpEarned)
            putInt(KEY_STREAK, newStreak)
            putString(KEY_LAST_DATE, today)
            putStringSet(KEY_COMPLETED, completed)
        }
        return xpEarned
    }

    fun calculateXp(correct: Int, total: Int): Int {
        val base = correct * 10
        val bonus = if (total > 0 && correct == total) 20 else 0
        return base + bonus
    }

    fun xpToLevel(xp: Int): Int = (xp / 100) + 1
    fun xpInCurrentLevel(xp: Int): Int = xp % 100
}

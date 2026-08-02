package com.morsemaster.app.data

import android.content.Context
import androidx.core.content.edit

/**
 * Simple SM-2-inspired spaced repetition.
 * Tracks per-letter error counts and surfaces the weakest letters first.
 */
object SpacedRepetition {
    private const val PREFS = "spaced_rep"

    fun recordAnswer(ctx: Context, letter: String, correct: Boolean) {
        val prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val key = "err_$letter"
        val current = prefs.getInt(key, 0)
        prefs.edit {
            putInt(key, if (correct) maxOf(0, current - 1) else current + 2)
        }
    }

    fun getWeakLetters(ctx: Context, topN: Int = 5): List<Pair<String, Int>> {
        val prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.all
            .filter { it.key.startsWith("err_") && (it.value as? Int ?: 0) > 0 }
            .map { it.key.removePrefix("err_") to (it.value as Int) }
            .sortedByDescending { it.second }
            .take(topN)
    }

    fun generateReviewExercises(ctx: Context): List<Exercise> {
        val weak = getWeakLetters(ctx, 8)
        if (weak.isEmpty()) return emptyList()
        return weak.flatMap { (letter, _) ->
            val morse = MorseCode.alphabet[letter.firstOrNull()?.uppercaseChar()] ?: return@flatMap emptyList()
            val wrongOptions = MorseCode.alphabet.values
                .filter { it != morse }.shuffled().take(3)
            listOf(
                Exercise(
                    question = letter,
                    questionType = QuestionType.LETTER_TO_MORSE,
                    correctAnswer = morse,
                    options = (wrongOptions + morse).shuffled()
                )
            )
        }
    }
}

package com.morsemaster.app.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morsemaster.app.data.*
import com.morsemaster.app.util.HapticFeedback
import com.morsemaster.app.util.MorseAudioPlayer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(lessonId: Int, onFinished: (correct: Int, total: Int) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lesson = LessonRepository.lessons.getOrNull(lessonId) ?: return
    var currentIndex by remember { mutableIntStateOf(0) }
    var correct by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }
    val exercise = lesson.exercises[currentIndex]

    Scaffold(topBar = { TopAppBar(title = { Text(lesson.title) }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / lesson.exercises.size },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )
            val questionLabel = when (exercise.questionType) {
                QuestionType.LETTER_TO_MORSE -> "Welcher Morse-Code steht für:"
                QuestionType.MORSE_TO_LETTER -> "Welcher Buchstabe steht für:"
            }
            Text(questionLabel, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))

            // Play audio button for morse->letter exercises
            if (exercise.questionType == QuestionType.MORSE_TO_LETTER && UserSettings.isAudioEnabled(context)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(exercise.question, fontSize = 42.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    IconButton(onClick = { scope.launch { MorseAudioPlayer.play(exercise.question) } }) {
                        Text("🔊", fontSize = 24.sp)
                    }
                }
            } else {
                Text(exercise.question, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(28.dp))

            exercise.options.forEach { option ->
                val containerColor = when {
                    !answered -> MaterialTheme.colorScheme.secondaryContainer
                    option == exercise.correctAnswer -> MaterialTheme.colorScheme.primaryContainer
                    option == selectedAnswer -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.secondaryContainer
                }
                Button(
                    onClick = {
                        if (!answered) {
                            selectedAnswer = option
                            answered = true
                            val isCorrect = option == exercise.correctAnswer
                            if (isCorrect) {
                                correct++
                                if (UserSettings.isHapticsEnabled(context)) HapticFeedback.correct(context)
                            } else {
                                if (UserSettings.isHapticsEnabled(context)) HapticFeedback.wrong(context)
                            }
                            SpacedRepetition.recordAnswer(context, exercise.question, isCorrect)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = containerColor)
                ) { Text(option, fontSize = 18.sp) }
            }

            if (answered) {
                Spacer(Modifier.height(20.dp))
                AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                    Text(
                        text = if (selectedAnswer == exercise.correctAnswer) "✅ Richtig!" else "❌ Falsch – ${exercise.correctAnswer}",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                Button(
                    onClick = {
                        if (currentIndex + 1 < lesson.exercises.size) {
                            currentIndex++; answered = false; selectedAnswer = null
                        } else {
                            val isPerfect = correct == lesson.exercises.size
                            UserProgress.recordLessonComplete(context, lessonId, correct, lesson.exercises.size)
                            val newXp = UserProgress.getXp(context)
                            val streak = UserProgress.getStreak(context)
                            val completedCount = UserProgress.getCompletedLessons(context).size
                            AchievementRepository.checkAndUnlock(context, newXp, streak, completedCount, isPerfect, false)
                            if (isPerfect && UserSettings.isHapticsEnabled(context)) HapticFeedback.celebrate(context)
                            onFinished(correct, lesson.exercises.size)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text(if (currentIndex + 1 < lesson.exercises.size) "Weiter" else "Ergebnisse ansehen") }
            }
        }
    }
}

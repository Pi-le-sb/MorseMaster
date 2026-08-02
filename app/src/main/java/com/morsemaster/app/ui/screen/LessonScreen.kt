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
import com.morsemaster.app.data.LessonRepository
import com.morsemaster.app.data.QuestionType
import com.morsemaster.app.data.UserProgress
import com.morsemaster.app.util.HapticFeedback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(lessonId: Int, onFinished: (correct: Int, total: Int) -> Unit) {
    val context = LocalContext.current
    val lesson = LessonRepository.lessons.getOrNull(lessonId) ?: return
    var currentIndex by remember { mutableIntStateOf(0) }
    var correct by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }

    val exercise = lesson.exercises[currentIndex]

    Scaffold(
        topBar = { TopAppBar(title = { Text(lesson.title) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
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
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = exercise.question,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

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
                            if (option == exercise.correctAnswer) {
                                correct++
                                HapticFeedback.correct(context)
                            } else {
                                HapticFeedback.wrong(context)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = containerColor)
                ) {
                    Text(option, fontSize = 18.sp)
                }
            }

            if (answered) {
                Spacer(modifier = Modifier.height(24.dp))
                // Feedback label
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    Text(
                        text = if (selectedAnswer == exercise.correctAnswer) "✅ Richtig!" else "❌ Falsch – ${exercise.correctAnswer}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                Button(
                    onClick = {
                        if (currentIndex + 1 < lesson.exercises.size) {
                            currentIndex++
                            answered = false
                            selectedAnswer = null
                        } else {
                            val xp = UserProgress.recordLessonComplete(context, lessonId, correct, lesson.exercises.size)
                            if (correct == lesson.exercises.size) HapticFeedback.celebrate(context)
                            onFinished(correct, lesson.exercises.size)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (currentIndex + 1 < lesson.exercises.size) "Weiter" else "Ergebnisse ansehen")
                }
            }
        }
    }
}

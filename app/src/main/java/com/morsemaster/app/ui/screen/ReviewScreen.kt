package com.morsemaster.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morsemaster.app.data.SpacedRepetition
import com.morsemaster.app.data.UserProgress
import com.morsemaster.app.util.HapticFeedback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(onFinished: () -> Unit) {
    val context = LocalContext.current
    val exercises = remember { SpacedRepetition.generateReviewExercises(context) }

    if (exercises.isEmpty()) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Wiederholung") }, navigationIcon = { IconButton(onClick = onFinished) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "") } }) }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎉", fontSize = 64.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("Keine schwachen Buchstaben!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Löse zuerst einige Lektionen.", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = onFinished) { Text("Zurück") }
                }
            }
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var correct by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }
    val exercise = exercises[currentIndex]

    Scaffold(
        topBar = { TopAppBar(title = { Text("Wiederholung – schwache Buchstaben") }, navigationIcon = { IconButton(onClick = onFinished) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "") } }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / exercises.size },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )
            Text("Welcher Morse-Code steht für:", style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(12.dp))
            Text(exercise.question, fontSize = 52.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 32.dp))

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
                            if (isCorrect) { correct++; HapticFeedback.correct(context) } else HapticFeedback.wrong(context)
                            SpacedRepetition.recordAnswer(context, exercise.question, isCorrect)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = containerColor)
                ) { Text(option, fontSize = 18.sp) }
            }

            if (answered) {
                Spacer(Modifier.height(20.dp))
                Text(
                    if (selectedAnswer == exercise.correctAnswer) "✅ Richtig!" else "❌ Falsch – ${exercise.correctAnswer}",
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (currentIndex + 1 < exercises.size) { currentIndex++; answered = false; selectedAnswer = null }
                        else { UserProgress.recordLessonComplete(context, -1, correct, exercises.size); onFinished() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text(if (currentIndex + 1 < exercises.size) "Weiter" else "Fertig") }
            }
        }
    }
}

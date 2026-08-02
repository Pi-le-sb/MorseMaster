package com.morsemaster.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(correct: Int, total: Int, onHome: () -> Unit) {
    val percentage = if (total > 0) (correct * 100) / total else 0
    val emoji = when {
        percentage >= 90 -> "🎉"
        percentage >= 60 -> "👍"
        else -> "💪"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emoji, fontSize = 72.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lektion abgeschlossen!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "$correct von $total richtig ($percentage%)",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onHome, modifier = Modifier.fillMaxWidth()) {
            Text("Zurück zur Startseite")
        }
    }
}

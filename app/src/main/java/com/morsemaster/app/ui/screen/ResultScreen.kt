package com.morsemaster.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morsemaster.app.data.UserProgress

@Composable
fun ResultScreen(correct: Int, total: Int, onHome: () -> Unit) {
    val context = LocalContext.current
    val percentage = if (total > 0) (correct * 100) / total else 0
    val xpEarned = UserProgress.calculateXp(correct, total)
    val streak = UserProgress.getStreak(context)
    val totalXp = UserProgress.getXp(context)
    val level = UserProgress.xpToLevel(totalXp)

    val emoji = when {
        percentage >= 90 -> "🎉"
        percentage >= 60 -> "👍"
        else -> "💪"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emoji, fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))
        Text("Lektion abgeschlossen!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("$correct von $total richtig ($percentage%)", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("+ $xpEarned XP", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
                if (correct == total) Text("🌟 Perfekt-Bonus: +20 XP!", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))
                Text("Gesamt: $totalXp XP  •  Level $level  •  🔥 $streak Tage",
                    style = MaterialTheme.typography.labelMedium)
            }
        }

        Spacer(Modifier.height(32.dp))
        Button(onClick = onHome, modifier = Modifier.fillMaxWidth()) {
            Text("Zurück zur Startseite")
        }
    }
}

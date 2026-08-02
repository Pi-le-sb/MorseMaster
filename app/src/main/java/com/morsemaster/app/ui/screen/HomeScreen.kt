package com.morsemaster.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morsemaster.app.data.LessonRepository
import com.morsemaster.app.data.UserProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onStartLesson: (Int) -> Unit) {
    val context = LocalContext.current
    var xp by remember { mutableIntStateOf(UserProgress.getXp(context)) }
    var streak by remember { mutableIntStateOf(UserProgress.getStreak(context)) }
    val completed by remember { mutableStateOf(UserProgress.getCompletedLessons(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MorseMaster", fontWeight = FontWeight.Bold) },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "XP", tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(4.dp))
                        Text("${xp} XP", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(12.dp))
                        Text("🔥 $streak", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // XP level bar
            val level = UserProgress.xpToLevel(xp)
            val xpInLevel = UserProgress.xpInCurrentLevel(xp)
            Text("Level $level", fontWeight = FontWeight.SemiBold)
            LinearProgressIndicator(
                progress = { xpInLevel / 100f },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            Text("$xpInLevel / 100 XP bis Level ${level + 1}", style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(20.dp))

            Text(
                text = "Lektionen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(LessonRepository.lessons) { _, lesson ->
                    val isDone = completed.contains(lesson.id)
                    Card(
                        onClick = { onStartLesson(lesson.id) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDone)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(lesson.title, fontWeight = FontWeight.SemiBold)
                                Text(lesson.description, style = MaterialTheme.typography.bodySmall)
                            }
                            if (isDone) Text("✅", fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

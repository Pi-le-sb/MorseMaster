package com.morsemaster.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun HomeScreen(
    onStartLesson: (Int) -> Unit,
    onOpenKeyboard: () -> Unit,
    onOpenAchievements: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenReview: () -> Unit
) {
    val context = LocalContext.current
    var xp by remember { mutableIntStateOf(UserProgress.getXp(context)) }
    var streak by remember { mutableIntStateOf(UserProgress.getStreak(context)) }
    val completed by remember { mutableStateOf(UserProgress.getCompletedLessons(context)) }
    val level = UserProgress.xpToLevel(xp)
    val xpInLevel = UserProgress.xpInCurrentLevel(xp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MorseMaster", fontWeight = FontWeight.Bold) },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 8.dp)) {
                        Icon(Icons.Default.Star, "XP", tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(2.dp))
                        Text("$xp XP", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(10.dp))
                        Text("🔥 $streak", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(4.dp))
                        IconButton(onClick = onOpenSettings) {
                            Icon(Icons.Default.Settings, "Einstellungen")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenKeyboard,
                    icon = { Icon(Icons.Default.Edit, null) },
                    label = { Text("Tastatur") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenReview,
                    icon = { Icon(Icons.Default.Refresh, null) },
                    label = { Text("Wiederholung") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenAchievements,
                    icon = { Icon(Icons.Default.EmojiEvents, null) },
                    label = { Text("Erfolge") }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                // Level card
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Level $level", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                            Text("$xpInLevel / 100 XP", style = MaterialTheme.typography.labelMedium)
                        }
                        Spacer(Modifier.height(6.dp))
                        LinearProgressIndicator(progress = { xpInLevel / 100f }, modifier = Modifier.fillMaxWidth())
                        Spacer(Modifier.height(4.dp))
                        Text("noch ${100 - xpInLevel} XP bis Level ${level + 1}", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            item {
                Text("Lektionen", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            itemsIndexed(LessonRepository.lessons) { _, lesson ->
                val isDone = completed.contains(lesson.id)
                Card(
                    onClick = { onStartLesson(lesson.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDone) MaterialTheme.colorScheme.primaryContainer
                                         else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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

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
import com.morsemaster.app.data.UserSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var hapticsOn by remember { mutableStateOf(UserSettings.isHapticsEnabled(context)) }
    var audioOn by remember { mutableStateOf(UserSettings.isAudioEnabled(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Zurück")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Feedback", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("📳 Haptisches Feedback", fontWeight = FontWeight.SemiBold)
                        Text("Vibriert bei richtigen/falschen Antworten", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = hapticsOn,
                        onCheckedChange = {
                            hapticsOn = it
                            UserSettings.setHaptics(context, it)
                        }
                    )
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("🔊 Audio-Feedback", fontWeight = FontWeight.SemiBold)
                        Text("Spielt Morse-Töne beim Eingeben ab", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = audioOn,
                        onCheckedChange = {
                            audioOn = it
                            UserSettings.setAudio(context, it)
                        }
                    )
                }
            }
        }
    }
}

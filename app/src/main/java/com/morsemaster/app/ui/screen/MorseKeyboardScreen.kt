package com.morsemaster.app.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morsemaster.app.data.MorseCode
import com.morsemaster.app.data.UserSettings
import com.morsemaster.app.util.HapticFeedback
import com.morsemaster.app.util.MorseAudioPlayer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorseKeyboardScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentSymbols by remember { mutableStateOf("") }  // e.g. ".-"
    var decodedText by remember { mutableStateOf("") }      // full decoded text
    var morseText by remember { mutableStateOf("") }        // display morse string

    fun decodeCurrent(): String {
        val ch = MorseCode.alphabet.entries.find { it.value == currentSymbols }?.key
        return ch?.toString() ?: "?"
    }

    fun commitLetter() {
        if (currentSymbols.isNotEmpty()) {
            val ch = decodeCurrent()
            decodedText += ch
            morseText += currentSymbols + " "
            currentSymbols = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Morse-Tastatur") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Zurück")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Output display
            Card(
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 80.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (decodedText.isEmpty()) "Tippe · und – um Zeichen einzugeben"
                               else decodedText,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (morseText.isNotEmpty()) {
                        Text(
                            text = morseText,
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Current symbol preview
            AnimatedContent(targetState = currentSymbols, label = "symbols") { syms ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val preview = if (syms.isEmpty()) "—" else "$syms  →  ${MorseCode.alphabet.entries.find { it.value == syms }?.key ?: "?"}"
                    Text(preview, fontSize = 22.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            // DOT and DASH buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        currentSymbols += "."
                        if (UserSettings.isHapticsEnabled(context)) HapticFeedback.correct(context)
                        if (UserSettings.isAudioEnabled(context)) scope.launch { MorseAudioPlayer.play(".") }
                    },
                    modifier = Modifier.weight(1f).height(80.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("·", fontSize = 36.sp, fontWeight = FontWeight.Black)
                }
                Button(
                    onClick = {
                        currentSymbols += "-"
                        if (UserSettings.isHapticsEnabled(context)) HapticFeedback.correct(context)
                        if (UserSettings.isAudioEnabled(context)) scope.launch { MorseAudioPlayer.play("-") }
                    },
                    modifier = Modifier.weight(1f).height(80.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("—", fontSize = 36.sp, fontWeight = FontWeight.Black)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Confirm letter
                OutlinedButton(
                    onClick = { commitLetter() },
                    modifier = Modifier.weight(1f)
                ) { Text("✓ Buchstabe") }

                // Space
                OutlinedButton(
                    onClick = {
                        commitLetter()
                        decodedText += " "
                        morseText += "/ "
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("␣ Leerzeichen") }

                // Delete
                IconButton(onClick = {
                    if (currentSymbols.isNotEmpty()) currentSymbols = currentSymbols.dropLast(1)
                    else if (decodedText.isNotEmpty()) {
                        decodedText = decodedText.dropLast(1)
                        morseText = morseText.trimEnd().dropLastWhile { it != ' ' }.trimEnd() + " "
                    }
                }) {
                    Icon(Icons.Default.Delete, "Löschen")
                }
            }

            Spacer(Modifier.height(20.dp))

            // Play full text as audio
            if (decodedText.isNotEmpty()) {
                Button(
                    onClick = {
                        val encoded = MorseCode.encode(decodedText)
                        scope.launch { MorseAudioPlayer.play(encoded) }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("🔊 Text als Morse abspielen")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { decodedText = ""; morseText = ""; currentSymbols = "" },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Alles löschen") }
            }
        }
    }
}

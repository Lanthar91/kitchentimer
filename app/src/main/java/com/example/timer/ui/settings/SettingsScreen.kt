package com.example.timer.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timer.prefs.PreferencesRepository
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, prefs: PreferencesRepository) {
    val scope = rememberCoroutineScope()
    val soundUri by prefs.soundUri.collectAsState(initial = null)
    val vibration by prefs.vibrationEnabled.collectAsState(initial = true)
    var soundText by remember { mutableStateOf(soundUri ?: "") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = soundText,
                onValueChange = { soundText = it },
                label = { Text("Sound URI") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = vibration, onCheckedChange = {
                    scope.launch { prefs.setVibration(it) }
                })
                Text("Vibration")
            }
            Button(onClick = {
                scope.launch { prefs.setSoundUri(soundText.ifBlank { null }) }
                navController.popBackStack()
            }) { Text("Save") }
        }
    }
}

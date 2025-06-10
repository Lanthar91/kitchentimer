package com.example.timer.ui.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timer.data.Recipe
import com.example.timer.data.RecipeRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(navController: NavController, repo: RecipeRepository, recipeId: Int) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var durations by remember { mutableStateOf("") }

    LaunchedEffect(recipeId) {
        if (recipeId != 0) {
            repo.get(recipeId)?.let {
                name = it.name
                durations = it.durations.joinToString(",")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (recipeId == 0) "New Recipe" else "Edit Recipe") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val list = durations.split(',').mapNotNull { it.toLongOrNull() }
                scope.launch {
                    repo.save(Recipe(id = recipeId, name = name, durations = list))
                    navController.popBackStack()
                }
            }) {
                Text("OK")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            OutlinedTextField(
                value = durations,
                onValueChange = { durations = it },
                label = { Text("Durations (sec, comma separated)") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        }
    }
}

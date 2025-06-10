package com.example.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timer.data.RecipeDatabase
import com.example.timer.data.RecipeRepository
import com.example.timer.ui.recipe.EditRecipeScreen
import com.example.timer.ui.recipe.RecipeListScreen
import com.example.timer.prefs.PreferencesRepository
import com.example.timer.ui.settings.SettingsScreen

@Composable
fun TimerApp(context: android.content.Context) {
    val db = remember { RecipeDatabase.get(context) }
    val repo = remember { RecipeRepository(db.recipeDao()) }
    val prefs = remember { PreferencesRepository(context) }
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable("list") { RecipeListScreen(navController, repo) }
        composable("edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            EditRecipeScreen(navController, repo, id)
        }
        composable("settings") { SettingsScreen(navController, prefs) }
    }
}

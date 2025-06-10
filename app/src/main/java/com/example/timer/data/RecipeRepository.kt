package com.example.timer.data

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val dao: RecipeDao) {
    val recipes: Flow<List<Recipe>> = dao.getAll()

    suspend fun get(id: Int): Recipe? = dao.getById(id)

    suspend fun save(recipe: Recipe) {
        if (recipe.id == 0) dao.insert(recipe) else dao.update(recipe)
    }

    suspend fun delete(recipe: Recipe) = dao.delete(recipe)
}

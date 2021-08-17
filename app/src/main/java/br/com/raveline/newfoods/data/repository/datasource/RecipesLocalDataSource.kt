package br.com.raveline.newfoods.data.repository.datasource

import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.Recipes
import kotlinx.coroutines.flow.Flow

interface RecipesLocalDataSource {
    suspend fun insertRecipe(recipes: RecipesEntity)
    fun readRecipes(): Flow<List<RecipesEntity>>
}
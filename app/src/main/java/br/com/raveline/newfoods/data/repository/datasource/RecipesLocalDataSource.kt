package br.com.raveline.newfoods.data.repository.datasource

import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow

interface RecipesLocalDataSource {
    /*RECIPES*/
    suspend fun insertRecipe(recipes: RecipesEntity)
    fun readRecipes(): Flow<List<RecipesEntity>>

    /*FAVORITES RECIPES*/
    suspend fun insertFavoriteRecipe(recipes: FavoriteEntity)
    fun readFavoritesRecipes():Flow<List<FavoriteEntity>>
    suspend fun deleteFavoriteRecipe(recipes: FavoriteEntity)
    suspend fun deleteAllFavoritesRecipes()
}
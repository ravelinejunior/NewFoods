package br.com.raveline.newfoods.domain.factory

import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.Recipes
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface FoodRepository {
    suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes>
    suspend fun insertFoodRecipes(recipes: RecipesEntity)
    fun readFoodRecipes(): Flow<List<RecipesEntity>>
    suspend fun getSearchedFood(queries: Map<String, String>):Response<Recipes>
    suspend fun insertFavoriteRecipe(recipes: FavoriteEntity)
    fun readFavoritesRecipes():Flow<List<FavoriteEntity>>
    suspend fun deleteFavoriteRecipe(recipes: FavoriteEntity)
    suspend fun deleteAllFavoritesRecipes()
}
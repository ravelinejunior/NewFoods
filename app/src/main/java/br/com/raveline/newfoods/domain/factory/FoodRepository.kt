package br.com.raveline.newfoods.domain.factory

import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.db.joke.entity.FoodJokeEntity
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.data.model.recipe.Recipes
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface FoodRepository {
    /*FOOD RECIPE*/
    suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes>
    suspend fun insertFoodRecipes(recipes: RecipesEntity)
    fun readFoodRecipes(): Flow<List<RecipesEntity>>
    suspend fun getSearchedFood(queries: Map<String, String>): Response<Recipes>

    /*FAVORITES RECIPES*/
    suspend fun insertFavoriteRecipe(recipes: FavoriteEntity)
    fun readFavoritesRecipes(): Flow<List<FavoriteEntity>>
    suspend fun deleteFavoriteRecipe(recipes: FavoriteEntity)
    suspend fun deleteAllFavoritesRecipes()

    /*FOOD JOKE*/
    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke>
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)
    fun readFoodJoke(): Flow<FoodJokeEntity>
}
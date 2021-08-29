package br.com.raveline.newfoods.data.repository.datasource

import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.data.model.recipe.Recipes
import retrofit2.Response

interface FoodRemoteRecipesDataSource {
    suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes>
    suspend fun getSearchedFoodRecipes(queries: Map<String, String>): Response<Recipes>
    /*JOKES*/
    suspend fun getFoodJoke(apiKey:String):Response<FoodJoke>

}
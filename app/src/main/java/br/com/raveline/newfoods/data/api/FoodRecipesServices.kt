package br.com.raveline.newfoods.data.api

import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.data.model.recipe.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesServices {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<Recipes>

    @GET("recipes/complexSearch")
    suspend fun getSearchedRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<Recipes>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(@Query("apiKey") apiKey: String): Response<FoodJoke>

}
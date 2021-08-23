package br.com.raveline.newfoods.data.repository.datasource

import br.com.raveline.newfoods.data.model.Recipes
import retrofit2.Response

interface FoodRemoteRecipesDataSource {
    suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes>
    suspend fun getSearchedFoodRecipes(queries: Map<String, String>): Response<Recipes>

}
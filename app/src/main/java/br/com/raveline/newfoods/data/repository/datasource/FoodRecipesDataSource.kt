package br.com.raveline.newfoods.data.repository.datasource

import br.com.raveline.newfoods.data.model.Recipes
import retrofit2.Response

interface FoodRecipesDataSource {
    suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes>
}
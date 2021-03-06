package br.com.raveline.newfoods.data.repository.datasource_impl

import br.com.raveline.newfoods.data.api.FoodRecipesServices
import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.data.model.recipe.Recipes
import br.com.raveline.newfoods.data.repository.datasource.FoodRemoteRecipesDataSource
import retrofit2.Response

class FoodRemoteRecipesDataSourceImpl(private val apiServices: FoodRecipesServices) :
    FoodRemoteRecipesDataSource {
    override suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes> {
        return apiServices.getRecipes(queries)
    }

    override suspend fun getSearchedFoodRecipes(queries: Map<String, String>): Response<Recipes> {
        return apiServices.getSearchedRecipes(queries)
    }

    override suspend fun getFoodJoke(apiKey:String): Response<FoodJoke> {
        return apiServices.getFoodJoke(apiKey)
    }
}
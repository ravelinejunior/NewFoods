package br.com.raveline.newfoods.data.repository.datasource_impl

import br.com.raveline.newfoods.data.api.FoodRecipesServices
import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.data.repository.datasource.FoodRecipesDataSource
import retrofit2.Response

class FoodRecipesDataSourceImpl(private val apiServices: FoodRecipesServices): FoodRecipesDataSource
{
    override suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes> {
        return apiServices.getRecipes(queries)
    }
}
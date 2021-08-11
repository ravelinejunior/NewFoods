package br.com.raveline.newfoods.data.repository

import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.data.repository.datasource.FoodRecipesDataSource
import br.com.raveline.newfoods.domain.factory.FoodRepository
import retrofit2.Response

class FoodRepositoryDataSourceImpl(private val foodRecipesDataSource: FoodRecipesDataSource) :
    FoodRepository {
    override suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes> {
        return foodRecipesDataSource.getFoodRecipes(queries)
    }
}
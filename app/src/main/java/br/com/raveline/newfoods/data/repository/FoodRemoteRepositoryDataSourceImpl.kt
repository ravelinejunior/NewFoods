package br.com.raveline.newfoods.data.repository

import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.data.repository.datasource.FoodRemoteRecipesDataSource
import br.com.raveline.newfoods.domain.factory.FoodRepository
import retrofit2.Response

class FoodRemoteRepositoryDataSourceImpl(private val foodRemoteRecipesDataSource: FoodRemoteRecipesDataSource) :
    FoodRepository {
    override suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes> {
        return foodRemoteRecipesDataSource.getFoodRecipes(queries)
    }
}
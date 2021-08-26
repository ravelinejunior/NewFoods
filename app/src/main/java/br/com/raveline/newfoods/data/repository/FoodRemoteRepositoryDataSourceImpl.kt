package br.com.raveline.newfoods.data.repository

import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.data.repository.datasource.FoodRemoteRecipesDataSource
import br.com.raveline.newfoods.data.repository.datasource.RecipesLocalDataSource
import br.com.raveline.newfoods.domain.factory.FoodRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FoodRemoteRepositoryDataSourceImpl(
    private val foodRemoteRecipesDataSource: FoodRemoteRecipesDataSource,
    private val localDataSource: RecipesLocalDataSource
) :
    FoodRepository {
    override suspend fun getFoodRecipes(queries: Map<String, String>): Response<Recipes> {
        return foodRemoteRecipesDataSource.getFoodRecipes(queries)
    }

    override suspend fun insertFoodRecipes(recipes: RecipesEntity) {
        localDataSource.insertRecipe(recipes)
    }

    override fun readFoodRecipes(): Flow<List<RecipesEntity>> {
        return localDataSource.readRecipes()
    }

    override suspend fun getSearchedFood(queries: Map<String, String>): Response<Recipes> {
        return foodRemoteRecipesDataSource.getSearchedFoodRecipes(queries)
    }

    override suspend fun insertFavoriteRecipe(recipes: FavoriteEntity) {
        localDataSource.insertFavoriteRecipe(recipes)
    }

    override fun readFavoritesRecipes(): Flow<List<FavoriteEntity>> {
        return localDataSource.readFavoritesRecipes()
    }

    override suspend fun deleteFavoriteRecipe(recipes: FavoriteEntity) {
        localDataSource.deleteFavoriteRecipe(recipes)
    }

    override suspend fun deleteAllFavoritesRecipes() {
        localDataSource.deleteAllFavoritesRecipes()
    }
}
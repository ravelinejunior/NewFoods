package br.com.raveline.newfoods.data.repository.datasource_impl

import br.com.raveline.newfoods.data.db.favorite.dao.FavoriteDao
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.db.joke.dao.FoodJokeDao
import br.com.raveline.newfoods.data.db.joke.entity.FoodJokeEntity
import br.com.raveline.newfoods.data.db.recipe.dao.RecipesDao
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.repository.datasource.RecipesLocalDataSource
import kotlinx.coroutines.flow.Flow

class RecipesLocalDataSourceImpl(
    private val recipesDao: RecipesDao,
    private val favoriteDao: FavoriteDao,
    private val foodJokeDao: FoodJokeDao
) : RecipesLocalDataSource {
    override suspend fun insertRecipe(recipes: RecipesEntity) {
        recipesDao.insertRecipes(recipes)
    }

    override fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    override suspend fun insertFavoriteRecipe(recipes: FavoriteEntity) {
        favoriteDao.insertFavoriteRecipe(recipes)
    }

    override fun readFavoritesRecipes(): Flow<List<FavoriteEntity>> {
        return favoriteDao.readFavoriteRecipes()
    }

    override suspend fun deleteFavoriteRecipe(recipes: FavoriteEntity) {
        favoriteDao.deleteFavoriteRecipe(recipes)
    }

    override suspend fun deleteAllFavoritesRecipes() {
        favoriteDao.deleteAllFavoritesRecipes()
    }

    override suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        foodJokeDao.insertFoodJoke(foodJokeEntity)
    }

    override fun readFoodJoke(): Flow<FoodJokeEntity> {
        return foodJokeDao.readFoodJoke()
    }

}
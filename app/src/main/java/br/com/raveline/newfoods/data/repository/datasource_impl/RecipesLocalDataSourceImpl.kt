package br.com.raveline.newfoods.data.repository.datasource_impl

import br.com.raveline.newfoods.data.db.recipe.dao.RecipesDao
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.data.repository.datasource.RecipesLocalDataSource
import kotlinx.coroutines.flow.Flow

class RecipesLocalDataSourceImpl(
    private val recipesDao: RecipesDao
) : RecipesLocalDataSource {
    override suspend fun insertRecipe(recipes: RecipesEntity) {
        recipesDao.insertRecipes(recipes)
    }

    override fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

}
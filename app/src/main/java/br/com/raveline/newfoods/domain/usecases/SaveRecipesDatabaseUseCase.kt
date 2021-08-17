package br.com.raveline.newfoods.domain.usecases

import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.domain.factory.FoodRepository

class SaveRecipesDatabaseUseCase(private val repository: FoodRepository) {
    suspend fun execute(recipes: RecipesEntity) {
        repository.insertFoodRecipes(recipes)
    }
}
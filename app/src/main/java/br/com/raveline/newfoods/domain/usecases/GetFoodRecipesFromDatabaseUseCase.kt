package br.com.raveline.newfoods.domain.usecases

import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.domain.factory.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetFoodRecipesFromDatabaseUseCase(
    private val foodRepository: FoodRepository
) {
    fun execute(): Flow<List<RecipesEntity>> {
        return foodRepository.readFoodRecipes()
    }
}
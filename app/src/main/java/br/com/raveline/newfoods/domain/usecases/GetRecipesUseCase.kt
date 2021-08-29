package br.com.raveline.newfoods.domain.usecases

import br.com.raveline.newfoods.data.model.recipe.Recipes
import br.com.raveline.newfoods.domain.factory.FoodRepository
import retrofit2.Response

class GetRecipesUseCase(private val foodRepository: FoodRepository) {
    suspend fun execute(queries: Map<String, String>): Response<Recipes> {
        return foodRepository.getFoodRecipes(queries)
    }
}
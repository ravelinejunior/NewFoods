package br.com.raveline.newfoods.domain.usecases

import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.domain.factory.FoodRepository
import retrofit2.Response

class GetSearchedUseCase(private val foodRepository: FoodRepository) {
    suspend fun execute(queries: Map<String, String>): Response<Recipes> {
        return foodRepository.getSearchedFood(queries)
    }
}
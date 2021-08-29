package br.com.raveline.newfoods.domain.usecases

import br.com.raveline.newfoods.data.db.joke.entity.FoodJokeEntity
import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.domain.factory.FoodRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetFoodJokeUseCase(private val foodRepository: FoodRepository) {
    suspend fun execute(apiKey: String): Response<FoodJoke> {
        return foodRepository.getFoodJoke(apiKey)
    }

    suspend fun executeInsertFoodEntity(foodJokeEntity: FoodJokeEntity) {
        foodRepository.insertFoodJoke(foodJokeEntity)
    }

     fun executeReadFoodEntity(): Flow<FoodJokeEntity> {
        return foodRepository.readFoodJoke()
    }
}
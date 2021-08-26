package br.com.raveline.newfoods.domain.usecases

import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.domain.factory.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: FoodRepository) {
    suspend fun executeSave(favoriteEntity: FavoriteEntity) {
        repository.insertFavoriteRecipe(favoriteEntity)
    }

    fun executeRead(): Flow<List<FavoriteEntity>> {
        return repository.readFavoritesRecipes()
    }

    suspend fun executeDeleteSingle(favoriteEntity: FavoriteEntity) {
        repository.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun executeDeleteAll() {
        repository.deleteAllFavoritesRecipes()
    }
}
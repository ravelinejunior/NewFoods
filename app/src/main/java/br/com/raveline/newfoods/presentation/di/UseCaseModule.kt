package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.domain.factory.FoodRepository
import br.com.raveline.newfoods.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRecipesUseCase(foodRepository: FoodRepository): GetRecipesUseCase {
        return GetRecipesUseCase(foodRepository)
    }

    @Provides
    @Singleton
    fun provideReadRecipesUseCase(foodRepository: FoodRepository): GetFoodRecipesFromDatabaseUseCase {
        return GetFoodRecipesFromDatabaseUseCase(foodRepository)
    }

    @Provides
    @Singleton
    fun provideInsertRecipesDatabaseUseCase(foodRepository: FoodRepository): SaveRecipesDatabaseUseCase {
        return SaveRecipesDatabaseUseCase(foodRepository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedRecipesUseCase(foodRepository: FoodRepository): GetSearchedUseCase {
        return GetSearchedUseCase(foodRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(foodRepository: FoodRepository): GetFavoritesUseCase {
        return GetFavoritesUseCase(foodRepository)
    }

    @Provides
    @Singleton
    fun provideGetFoodJokeRepository(foodRepository: FoodRepository): GetFoodJokeUseCase {
        return GetFoodJokeUseCase(foodRepository)
    }
}
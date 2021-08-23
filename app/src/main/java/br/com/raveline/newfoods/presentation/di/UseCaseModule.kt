package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.domain.factory.FoodRepository
import br.com.raveline.newfoods.domain.usecases.GetFoodRecipesFromDatabaseUseCase
import br.com.raveline.newfoods.domain.usecases.GetRecipesUseCase
import br.com.raveline.newfoods.domain.usecases.GetSearchedUseCase
import br.com.raveline.newfoods.domain.usecases.SaveRecipesDatabaseUseCase
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
}
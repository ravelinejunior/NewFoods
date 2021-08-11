package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.data.repository.datasource_impl.FoodRecipesDataSourceImpl
import br.com.raveline.newfoods.domain.factory.FoodRepository
import br.com.raveline.newfoods.domain.usecases.GetRecipesUseCase
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
    fun provideGetRecipesUseCase(foodRepository: FoodRepository):GetRecipesUseCase{
        return GetRecipesUseCase(foodRepository)
    }
}
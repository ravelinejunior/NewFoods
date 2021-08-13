package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.data.repository.FoodRepositoryDataSourceImpl
import br.com.raveline.newfoods.data.repository.datasource.FoodRecipesDataSource
import br.com.raveline.newfoods.domain.factory.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryDataSourceModule {

    @Provides
    @Singleton
    fun provideFoodRepositoryDataSource(
        foodRecipesDataSource: FoodRecipesDataSource
    ): FoodRepository {
        return FoodRepositoryDataSourceImpl(foodRecipesDataSource)
    }
}
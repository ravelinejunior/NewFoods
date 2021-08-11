package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.data.api.FoodRecipesServices
import br.com.raveline.newfoods.data.repository.datasource.FoodRecipesDataSource
import br.com.raveline.newfoods.data.repository.datasource_impl.FoodRecipesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceUseCase {

    @Provides
    @Singleton
    fun provideFoodRecipesDataSource(api: FoodRecipesServices): FoodRecipesDataSource {
        return FoodRecipesDataSourceImpl(api)
    }
}
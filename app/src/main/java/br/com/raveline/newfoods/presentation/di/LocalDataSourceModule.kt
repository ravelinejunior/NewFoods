package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.data.db.recipe.dao.RecipesDao
import br.com.raveline.newfoods.data.repository.datasource.RecipesLocalDataSource
import br.com.raveline.newfoods.data.repository.datasource_impl.RecipesLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(recipesDao: RecipesDao): RecipesLocalDataSource {
        return RecipesLocalDataSourceImpl(recipesDao)
    }
}
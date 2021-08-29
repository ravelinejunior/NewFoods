package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.data.db.favorite.dao.FavoriteDao
import br.com.raveline.newfoods.data.db.joke.dao.FoodJokeDao
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
    fun provideLocalDataSource(
        recipesDao: RecipesDao,
        favoriteDao: FavoriteDao,
        foodJokeDao: FoodJokeDao
    ): RecipesLocalDataSource {
        return RecipesLocalDataSourceImpl(recipesDao, favoriteDao, foodJokeDao)
    }
}
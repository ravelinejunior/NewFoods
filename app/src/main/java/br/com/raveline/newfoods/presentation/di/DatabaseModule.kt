package br.com.raveline.newfoods.presentation.di

import android.app.Application
import androidx.room.Room
import br.com.raveline.newfoods.data.db.RecipesDatabase
import br.com.raveline.newfoods.data.db.favorite.dao.FavoriteDao
import br.com.raveline.newfoods.data.db.recipe.dao.RecipesDao
import br.com.raveline.newfoods.utils.Constants.Companion.RECIPES_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): RecipesDatabase {
        return Room.databaseBuilder(application, RecipesDatabase::class.java, RECIPES_DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(recipesDatabase: RecipesDatabase): RecipesDao {
        return recipesDatabase.recipesDao()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(recipesDatabase: RecipesDatabase): FavoriteDao {
        return recipesDatabase.favoritesDao()
    }

}
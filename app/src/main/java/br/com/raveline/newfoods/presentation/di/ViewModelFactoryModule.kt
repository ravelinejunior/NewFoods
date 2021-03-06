package br.com.raveline.newfoods.presentation.di

import android.app.Application
import br.com.raveline.newfoods.data.datastore.DataStoreRepository
import br.com.raveline.newfoods.domain.usecases.*
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import br.com.raveline.newfoods.presentation.viewmodel.RecipesViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {


    @Singleton
    @Provides
    fun provideMainViewModel(
        getRecipesUseCase: GetRecipesUseCase,
        saveRecipesDatabaseUseCase: SaveRecipesDatabaseUseCase,
        getFoodRecipesFromDatabaseUseCase: GetFoodRecipesFromDatabaseUseCase,
        getSearchedUseCase: GetSearchedUseCase,
        getFavoritesUseCase: GetFavoritesUseCase,
        getFoodJokeUseCase: GetFoodJokeUseCase,
        application: Application
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getRecipesUseCase,
            saveRecipesDatabaseUseCase,
            getFoodRecipesFromDatabaseUseCase,
            getSearchedUseCase,
            getFavoritesUseCase,
            getFoodJokeUseCase,
            application
        )
    }

    @Singleton
    @Provides
    fun provideRecipesViewModel(
        application: Application,
        dataStoreRepository: DataStoreRepository
    ): RecipesViewModelFactory {
        return RecipesViewModelFactory(
            application,
            dataStoreRepository
        )
    }

}
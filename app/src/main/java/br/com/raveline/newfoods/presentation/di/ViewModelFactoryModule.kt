package br.com.raveline.newfoods.presentation.di

import android.app.Application
import br.com.raveline.newfoods.domain.usecases.GetFoodRecipesFromDatabaseUseCase
import br.com.raveline.newfoods.domain.usecases.GetRecipesUseCase
import br.com.raveline.newfoods.domain.usecases.SaveRecipesDatabaseUseCase
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
        application: Application
    ): MainViewModelFactory {
        return MainViewModelFactory(getRecipesUseCase, application)
    }

    @Singleton
    @Provides
    fun provideRecipesViewModel(
        application: Application,
        saveRecipesDatabaseUseCase: SaveRecipesDatabaseUseCase,
        getFoodRecipesFromDatabaseUseCase: GetFoodRecipesFromDatabaseUseCase
    ): RecipesViewModelFactory {
        return RecipesViewModelFactory(
            application,
            saveRecipesDatabaseUseCase,
            getFoodRecipesFromDatabaseUseCase
        )
    }

}
package br.com.raveline.newfoods.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.newfoods.domain.usecases.GetFoodRecipesFromDatabaseUseCase
import br.com.raveline.newfoods.domain.usecases.GetRecipesUseCase
import br.com.raveline.newfoods.domain.usecases.GetSearchedUseCase
import br.com.raveline.newfoods.domain.usecases.SaveRecipesDatabaseUseCase

class MainViewModelFactory(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val saveRecipesDatabaseUseCase: SaveRecipesDatabaseUseCase,
    private val getFoodRecipesFromDatabaseUseCase: GetFoodRecipesFromDatabaseUseCase,
    private val getSearchedUseCase: GetSearchedUseCase,
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            getRecipesUseCase,
            saveRecipesDatabaseUseCase,
            getFoodRecipesFromDatabaseUseCase,
            getSearchedUseCase,
            application
        ) as T
    }
}
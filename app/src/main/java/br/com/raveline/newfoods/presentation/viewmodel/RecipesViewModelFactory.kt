package br.com.raveline.newfoods.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.newfoods.domain.usecases.GetFoodRecipesFromDatabaseUseCase
import br.com.raveline.newfoods.domain.usecases.SaveRecipesDatabaseUseCase

class RecipesViewModelFactory(
    private val application: Application,
    private val saveRecipesDatabaseUseCase: SaveRecipesDatabaseUseCase,
    private val getFoodRecipesFromDatabaseUseCase: GetFoodRecipesFromDatabaseUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecipesViewModel(
            application, saveRecipesDatabaseUseCase, getFoodRecipesFromDatabaseUseCase,
        ) as T
    }
}
package br.com.raveline.newfoods.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.newfoods.domain.usecases.GetRecipesUseCase

class MainViewModelFactory(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getRecipesUseCase, application) as T
    }
}
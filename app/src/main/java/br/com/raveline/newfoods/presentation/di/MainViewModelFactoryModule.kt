package br.com.raveline.newfoods.presentation.di

import android.app.Application
import br.com.raveline.newfoods.domain.usecases.GetRecipesUseCase
import br.com.raveline.newfoods.presentation.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MainViewModelFactoryModule {

    fun provideMainViewModel(
        getRecipesUseCase: GetRecipesUseCase,
        application: Application
    ): MainViewModelFactory {
        return MainViewModelFactory(getRecipesUseCase, application)
    }

}
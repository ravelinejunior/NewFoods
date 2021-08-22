package br.com.raveline.newfoods.presentation.di

import br.com.raveline.newfoods.utils.listeners.NetworkListeners
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkListenerModule {

    @Provides
    @Singleton
    fun provideNetworkListener(): NetworkListeners {
        return NetworkListeners()
    }

}
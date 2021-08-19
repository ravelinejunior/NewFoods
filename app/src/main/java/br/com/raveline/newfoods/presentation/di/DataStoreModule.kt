package br.com.raveline.newfoods.presentation.di

import android.content.Context
import br.com.raveline.newfoods.data.datastore.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }
}
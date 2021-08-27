package br.com.raveline.newfoods.presentation.di

import androidx.fragment.app.FragmentActivity
import br.com.raveline.newfoods.presentation.ui.adapter.detail.ingredients.IngredientsAdapter
import br.com.raveline.newfoods.presentation.ui.adapter.favorites.FavoriteRecipesAdapter
import br.com.raveline.newfoods.presentation.ui.adapter.recipes.RecipesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {


    @Provides
    @Singleton
    fun provideRecipesAdapter(): RecipesAdapter {
        return RecipesAdapter()
    }

    @Provides
    @Singleton
    fun provideIngredientsAdapter(): IngredientsAdapter {
        return IngredientsAdapter()
    }

    @Provides
    @Singleton
    fun provideRequiredFragment(): FragmentActivity {
        return FragmentActivity()
    }

    @Provides
    @Singleton
    fun provideFavoriteRecipe( requiredActivity: FragmentActivity): FavoriteRecipesAdapter {
        return FavoriteRecipesAdapter(requiredActivity)
    }
}
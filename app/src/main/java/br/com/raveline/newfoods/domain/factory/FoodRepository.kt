package br.com.raveline.newfoods.domain.factory

import br.com.raveline.newfoods.data.model.Recipes
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response


interface FoodRepository {
    suspend fun getFoodRecipes(queries: Map<String, String>):Response<Recipes>
}
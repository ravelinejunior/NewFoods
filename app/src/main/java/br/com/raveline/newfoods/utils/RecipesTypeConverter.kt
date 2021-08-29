package br.com.raveline.newfoods.utils

import androidx.room.TypeConverter
import br.com.raveline.newfoods.data.model.recipe.Recipe
import br.com.raveline.newfoods.data.model.recipe.Recipes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    //converte para string
    @TypeConverter
    fun foodRecipeToString(recipes: Recipes): String {
        return gson.toJson(recipes)
    }

    //converte string para recipes
    @TypeConverter
    fun stringToRecipes(foodString: String): Recipes {
        val listType = object : TypeToken<Recipes>() {}.type
        return gson.fromJson(foodString, listType)
    }

    /*CONVERTER UM UNICO OBJETO RECIPE PARA STRING E VICE-VERSA*/
    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipeSingleObject(value: String): Recipe {
        val listType = object : TypeToken<Recipe>() {}.type
        return gson.fromJson(value, listType)
    }

}
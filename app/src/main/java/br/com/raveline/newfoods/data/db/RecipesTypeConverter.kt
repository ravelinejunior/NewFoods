package br.com.raveline.newfoods.data.db

import androidx.room.TypeConverter
import br.com.raveline.newfoods.data.model.Recipes
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

}
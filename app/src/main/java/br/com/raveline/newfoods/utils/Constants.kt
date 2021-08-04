package br.com.raveline.newfoods.utils

class Constants {
    companion object{
        const val BASE_URL = "https://api.spoonacular.com/"
        const val API_KEY = "5b40082bdb33448fb8aedd940dad43fd"

        //Api query keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room
        const val RECIPES_TABLE_NAME = "recipes_table"
        const val RECIPES_DATABASE_NAME = "recipes_database"

        //Bottom Sheet and Preferences
        const val PREFERENCES_NAME = "foody_preferences"
        const val DEFAULT_MEAL_TYPE = "main_course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "150"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"
    }
}
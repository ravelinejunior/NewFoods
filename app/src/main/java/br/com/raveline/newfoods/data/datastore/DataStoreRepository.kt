package br.com.raveline.newfoods.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_DIET_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.PREFERENCES_DIET_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import br.com.raveline.newfoods.utils.Constants.Companion.PREFERENCES_MEAL_TYPE
import br.com.raveline.newfoods.utils.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import br.com.raveline.newfoods.utils.Constants.Companion.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepository(private val context: Context) {
    private object PreferencesKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    suspend fun saveMealAndDiet(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.selectedMealType] = mealType
            prefs[PreferencesKeys.selectedMealTypeId] = mealTypeId
            prefs[PreferencesKeys.selectedDietType] = dietType
            prefs[PreferencesKeys.selectedDietTypeId] = dietTypeId

        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map { prefs ->
            val selectedMealType = prefs[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = prefs[PreferencesKeys.selectedMealTypeId] ?: 0
            val selectedDietType = prefs[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = prefs[PreferencesKeys.selectedDietTypeId] ?: 0

            MealAndDietType(
                selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId
            )
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)
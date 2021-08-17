package br.com.raveline.newfoods.data.db.recipe.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: RecipesEntity)

    @Query("SELECT * FROM RECIPES_TABLE")
    fun readRecipes(): Flow<List<RecipesEntity>>

}
package br.com.raveline.newfoods.data.db.favorite.dao

import androidx.room.*
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(recipe: FavoriteEntity)

    @Query("SELECT * FROM favorite_recipe_table ORDER BY id DESC")
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(recipe: FavoriteEntity)

    @Query("DELETE FROM favorite_recipe_table")
    suspend fun deleteAllFavoritesRecipes()

}
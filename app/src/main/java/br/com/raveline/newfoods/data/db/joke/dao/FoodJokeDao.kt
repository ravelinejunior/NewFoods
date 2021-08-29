package br.com.raveline.newfoods.data.db.joke.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.raveline.newfoods.data.db.joke.entity.FoodJokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodJokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM FOOD_JOKE_TABLE")
    fun readFoodJoke(): Flow<FoodJokeEntity>
}
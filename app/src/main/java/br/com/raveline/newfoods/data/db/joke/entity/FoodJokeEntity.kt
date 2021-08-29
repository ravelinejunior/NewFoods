package br.com.raveline.newfoods.data.db.joke.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.raveline.newfoods.data.model.joke.FoodJoke
import br.com.raveline.newfoods.utils.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
data class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

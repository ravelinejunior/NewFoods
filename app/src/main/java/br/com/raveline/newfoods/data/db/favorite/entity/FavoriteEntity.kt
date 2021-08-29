package br.com.raveline.newfoods.data.db.favorite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.raveline.newfoods.data.model.recipe.Recipe
import br.com.raveline.newfoods.utils.Constants.Companion.FAVORITES_TABLE_NAME

@Entity(tableName = FAVORITES_TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var recipe: Recipe
)
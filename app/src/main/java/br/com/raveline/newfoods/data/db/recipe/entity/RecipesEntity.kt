package br.com.raveline.newfoods.data.db.recipe.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.utils.Constants.Companion.RECIPES_TABLE_NAME

@Entity(tableName = RECIPES_TABLE_NAME)
class RecipesEntity(var recipes: Recipes) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

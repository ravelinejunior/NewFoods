package br.com.raveline.newfoods.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.raveline.newfoods.data.db.recipe.dao.RecipesDao
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.utils.RecipesTypeConverter

@Database(
    entities = [RecipesEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}
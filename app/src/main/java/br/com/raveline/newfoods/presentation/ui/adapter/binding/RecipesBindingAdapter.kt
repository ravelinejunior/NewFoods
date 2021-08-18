package br.com.raveline.newfoods.presentation.ui.adapter.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.raveline.newfoods.data.db.recipe.entity.RecipesEntity
import br.com.raveline.newfoods.data.model.Recipes
import br.com.raveline.newfoods.utils.Resource

class RecipesBindingAdapter {
    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: Resource<Recipes>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is Resource.Loading) {
                imageView.visibility = View.GONE
            } else if (apiResponse is Resource.Success) {
                imageView.visibility = View.GONE
            }
        }
    }
}
package br.com.raveline.newfoods.presentation.ui.adapter.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.model.Recipe
import br.com.raveline.newfoods.presentation.ui.fragment.recipes.RecipesFragmentDirections
import coil.load
import org.jsoup.Jsoup

class BindingAdapter {
    companion object {

        //parsear dados da web para remover as tags de html
        @JvmStatic
        @BindingAdapter("parseHTML")
        fun parseHTML(textView: TextView, description: String?) {
            if (description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

        @JvmStatic
        @BindingAdapter("onRecipeClickListener")
        fun onRecipeClickListener(recipesRowLayout: ConstraintLayout, recipe: Recipe) {
            recipesRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipesFragmentDirections.actionRecipesFragmentIdToDetailsActivity(recipe)
                    recipesRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.e("onRecipeClickListener", e.printStackTrace().toString())
                }
            }
        }

        @JvmStatic
        @BindingAdapter("setImageRecipe")
        fun setImageRecipe(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(400)
                error(R.drawable.no_image_loading)
            }
        }

        @JvmStatic
        @BindingAdapter("setNumberOfLikes")
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @JvmStatic
        @BindingAdapter("setNumberOfMinutes")
        fun setNumberOfMinutes(textView: TextView, readyInMinutes: Int) {
            textView.text = readyInMinutes.toString()
        }

        @JvmStatic
        @BindingAdapter("setTextAndColorForVegetarian")
        fun setTextAndColorForVegetarian(view: View, isVeg: Boolean) {
            if (isVeg) {
                when (view) {
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }

                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }

        }
    }
}
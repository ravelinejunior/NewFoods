package br.com.raveline.newfoods.presentation.ui.adapter.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import br.com.raveline.newfoods.R
import coil.load
import coil.size.Scale

class BindingAdapter {
    companion object {

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
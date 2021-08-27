package br.com.raveline.newfoods.presentation.ui.adapter.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.presentation.ui.adapter.favorites.FavoriteRecipesAdapter

class FavoriteBindingAdapter {
    companion object {

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoriteEntity: List<FavoriteEntity>?,
            mAdapter: FavoriteRecipesAdapter?
        ) {
            if (favoriteEntity.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.GONE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.GONE
                    }
                    is TextView -> {
                        view.visibility = View.GONE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.differ?.submitList(favoriteEntity)
                    }
                }
            }
        }
    }
}
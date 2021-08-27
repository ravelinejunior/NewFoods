package br.com.raveline.newfoods.presentation.ui.adapter.favorites

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.db.favorite.entity.FavoriteEntity
import br.com.raveline.newfoods.databinding.ItemFavoriteRecipesRowBinding
import br.com.raveline.newfoods.presentation.ui.fragment.favorites.FavoritesFragmentDirections
import br.com.raveline.newfoods.utils.RecipesDiffUtil

class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private val callback = object : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var favoriteList = emptyList<FavoriteEntity>()

    inner class MyViewHolder(private val binding: ItemFavoriteRecipesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteEntity: FavoriteEntity) {
            binding.favoritesEntity = favoriteEntity
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavoriteRecipesRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (differ.currentList.isNotEmpty()) {
            holder.bind(differ.currentList[position])
        } else {
            holder.bind(favoriteList[position])
        }

        /*CLICK LISTENERS*/
        holder.itemView.apply {

            /*SINGLE*/
            val action =
                FavoritesFragmentDirections.actionFavoritesFragmentIdToDetailsActivity(differ.currentList[position].recipe)
            setOnClickListener {
                findNavController().navigate(action)
            }

        }

        holder.itemView.setOnLongClickListener {
            /*LONG CLICK*/
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.isNotEmpty()) differ.currentList.size
        else favoriteList.size
    }

    fun setData(favorites: List<FavoriteEntity>) {
        val favoritesDiffUtil = RecipesDiffUtil(favoriteList, favorites)
        val diffUtilResult = DiffUtil.calculateDiff(favoritesDiffUtil)
        favoriteList = favorites
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_favorite_longclick, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }
}
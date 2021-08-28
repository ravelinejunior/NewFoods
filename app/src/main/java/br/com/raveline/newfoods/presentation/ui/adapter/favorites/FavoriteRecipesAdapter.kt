package br.com.raveline.newfoods.presentation.ui.adapter.favorites

import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
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
import kotlinx.android.synthetic.main.item_favorite_recipes_row.view.*

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
    private var selectedFavorites = arrayListOf<FavoriteEntity>()
    private var multiSelected = false

    private var myViewHolders = arrayListOf<MyViewHolder>()

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

        myViewHolders.add(holder)

        val currentRecipe: FavoriteEntity
        if (differ.currentList.isNotEmpty()) {
            currentRecipe = differ.currentList[position]
            holder.bind(currentRecipe)
        } else {
            currentRecipe = favoriteList[position]
            holder.bind(currentRecipe)
        }

        /*CLICK LISTENERS*/
        holder.itemView.setOnClickListener {
            /*VERIFICAR SE ESTÁ NO MODO DE SELEÇÃO MULTIPLA */
            if (multiSelected) {
                applySelection(holder, currentRecipe)
            } else {
                /*SINGLE*/
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentIdToDetailsActivity(differ.currentList[position].recipe)

                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.setOnLongClickListener {
            /*LONG CLICK*/
            if (!multiSelected) {
                requireActivity.startActionMode(this)
                multiSelected = !multiSelected
                applySelection(holder, currentRecipe)
                true
            } else if (selectedFavorites.isEmpty() && multiSelected) {
                multiSelected = !multiSelected
                false
            } else {
                applySelection(holder, currentRecipe)
                true
            }


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
        applyBarColor(R.color.dark_orange)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        applyBarColor(R.color.dark)
        multiSelected = false
        selectedFavorites.clear()

        /*PERCORRER CADA HOLDER E REMOVER ESTILIZAÇÃO*/
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.white, R.color.lightMediumGray, 0, 0)
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoriteEntity) {
        /*VERIFICAR SE RECEITA ESTÁ SELECIONADA OU NÃO*/
        if (selectedFavorites.contains(currentRecipe)) {
            selectedFavorites.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.white, R.color.lightMediumGray, 0, 0)
        } else {
            selectedFavorites.add(currentRecipe)
            changeRecipeStyle(holder, R.color.light_orange_alpha, R.color.dark_orange, 4, 10)
        }
    }

    private fun changeRecipeStyle(
        holder: MyViewHolder,
        backgroundColor: Int,
        strokeColor: Int,
        strokeWidth: Int,
        padding: Int
    ) {
        holder.itemView.materialCard_favoriteRow_layout_id.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )

        holder.itemView.card_image_favoritesRow_id.setPadding(padding)

        holder.itemView.materialCard_favoriteRow_layout_id.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)

        holder.itemView.materialCard_favoriteRow_layout_id.strokeWidth = strokeWidth

    }

    private fun applyBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

}
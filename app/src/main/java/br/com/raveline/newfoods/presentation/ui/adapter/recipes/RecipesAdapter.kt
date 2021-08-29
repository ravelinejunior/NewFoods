package br.com.raveline.newfoods.presentation.ui.adapter.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.newfoods.data.model.recipe.Recipe
import br.com.raveline.newfoods.data.model.recipe.Recipes
import br.com.raveline.newfoods.databinding.ItemRecipesRowBinding
import br.com.raveline.newfoods.utils.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    private var recipesList = emptyList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRecipesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe =
            if (differ.currentList.size > 0) differ.currentList[position] else recipesList[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size > 0) differ.currentList.size else recipesList.size
    }

    class MyViewHolder(private val binding: ItemRecipesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            binding.executePendingBindings()
            // Log.i("Adapter", recipe.toString())
        }

    }

    fun setRecipeData(newData: Recipes) {
        val recipesDiffUtil = RecipesDiffUtil(recipesList, newData.recipes!!)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipesList = newData.recipes
        diffUtilResult.dispatchUpdatesTo(this)
    }


}
package br.com.raveline.newfoods.presentation.ui.adapter.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.newfoods.data.model.Recipe
import br.com.raveline.newfoods.databinding.ItemRecipesRowBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecipesRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = differ.currentList[position]
    }

    override fun getItemCount(): Int = differ.currentList.size

    class MyViewHolder(private val binding: ItemRecipesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(recipe: Recipe){
                binding.executePendingBindings()
            }

    }



}
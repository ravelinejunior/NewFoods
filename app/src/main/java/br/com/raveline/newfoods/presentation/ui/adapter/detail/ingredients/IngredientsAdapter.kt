package br.com.raveline.newfoods.presentation.ui.adapter.detail.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.newfoods.R
import br.com.raveline.newfoods.data.model.ExtendedIngredient
import br.com.raveline.newfoods.databinding.ItemIngredientsRowBinding
import br.com.raveline.newfoods.utils.Constants.Companion.BASE_IMAGE_URL
import br.com.raveline.newfoods.utils.RecipesDiffUtil
import coil.load
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
        override fun areItemsTheSame(
            oldItem: ExtendedIngredient,
            newItem: ExtendedIngredient
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExtendedIngredient,
            newItem: ExtendedIngredient
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var ingredients = emptyList<ExtendedIngredient>()


    inner class MyViewHolder(private val iBinding: ItemIngredientsRowBinding) :
        RecyclerView.ViewHolder(iBinding.root) {

        fun bind(ingredient: ExtendedIngredient) {
            iBinding.apply {
                imageViewItemIngredientsId.load(BASE_IMAGE_URL + ingredient.image){
                    crossfade(400)
                    error(R.drawable.no_image_loading)
                }
                titleTextViewItemIngredientsId.text = ingredient.name?.capitalize(Locale.ROOT)
                amountTextViewItemIngredientsId.text = ingredient.amount.toString()
                unitTextViewItemIngredientsId.text = ingredient.unit
                consistecyTextViewItemIngredientsId.text = ingredient.consistency
                originalTextViewItemIngredientsId.text = ingredient.original
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemIngredientsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (differ.currentList.isEmpty() && ingredients.isNotEmpty()) differ.submitList(ingredients)
        val ingredient = differ.currentList[position]
        holder.bind(ingredient)

    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size > 0) differ.currentList.size
        else ingredients.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredients, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredients = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
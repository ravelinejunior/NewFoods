package br.com.raveline.newfoods.utils

import androidx.recyclerview.widget.DiffUtil
import br.com.raveline.newfoods.data.model.Recipe

class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

}
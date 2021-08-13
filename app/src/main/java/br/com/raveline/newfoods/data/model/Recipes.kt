package br.com.raveline.newfoods.data.model


import com.google.gson.annotations.SerializedName

data class Recipes(
    @SerializedName("results")
    val recipes: List<Recipe>?
)
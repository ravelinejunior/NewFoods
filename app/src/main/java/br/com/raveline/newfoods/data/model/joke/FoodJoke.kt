package br.com.raveline.newfoods.data.model.joke

import com.google.gson.annotations.SerializedName

data class FoodJoke(
    @SerializedName("text")
    val text: String
)

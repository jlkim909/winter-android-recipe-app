package com.surivalcoding.composerecipeapp.data.model

import androidx.annotation.DrawableRes

class HomeRecipe(
    val id: Int,
    @DrawableRes
    val foodIconUrl: Int,
    val cookingMinute: Int,
    val title: String,
    val rating: Double,
)
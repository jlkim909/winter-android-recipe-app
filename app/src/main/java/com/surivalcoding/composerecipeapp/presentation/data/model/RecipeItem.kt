package com.surivalcoding.composerecipeapp.presentation.data.model

data class RecipeItem(
    val thumbnailUrl: String,
    val cookingMinute: Int,
    val title: String,
    val authorName:String,
    val rating: Double,
    val isBookmarked: Boolean,
)

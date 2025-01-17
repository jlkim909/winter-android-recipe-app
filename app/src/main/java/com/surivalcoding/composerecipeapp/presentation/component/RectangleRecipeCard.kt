package com.surivalcoding.composerecipeapp.presentation.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.surivalcoding.composerecipeapp.R
import com.surivalcoding.composerecipeapp.presentation.data.model.RecipeItem
import com.surivalcoding.composerecipeapp.ui.theme.ComposeRecipeAppTheme

@Composable
fun RectangleRecipeCard(
    recipeItem: RecipeItem,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.traditional_spare_ribs_baked),
) {
    RecipeCard(
        recipeItem = recipeItem,
        shouldShowRecipeMetadata = true,
        contentDescription = contentDescription,
        modifier = modifier.aspectRatio(2.1f),
        placeholder = placeholder,
    )
}

@Preview
@Composable
fun RecipeCardPreview() {
    val recipeItem = RecipeItem(
        title = "Lamb chops with fruity couscous and mint\n\n",
        rating = 4.0,
        thumbnailUrl = "",
        authorName = "Spicy Nelly",
        cookingMinute = 20,
        isBookmarked = false
    )
    ComposeRecipeAppTheme {
        RectangleRecipeCard(
            modifier = Modifier.width(315.dp),
            recipeItem = recipeItem,
            contentDescription = null,
        )
    }
}
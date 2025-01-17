package com.surivalcoding.composerecipeapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surivalcoding.composerecipeapp.R
import com.surivalcoding.composerecipeapp.data.mock.fakeSavedRecipe
import com.surivalcoding.composerecipeapp.data.model.SavedRecipe
import com.surivalcoding.composerecipeapp.ui.CraIcons
import com.surivalcoding.composerecipeapp.ui.component.FoodImageBackground
import com.surivalcoding.composerecipeapp.ui.component.IconToggleButton
import com.surivalcoding.composerecipeapp.ui.theme.AppColors
import com.surivalcoding.composerecipeapp.ui.theme.AppTextStyles
import com.surivalcoding.composerecipeapp.ui.theme.ComposeRecipeAppTheme

@Composable
fun RecipeCard(
    savedRecipe: SavedRecipe,
    contentDescription: String?,
    shouldShowRecipeMetadata: Boolean = false,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.traditional_spare_ribs_baked),
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        FoodImageBackground(
            imageUrl = savedRecipe.thumbnailUrl,
            placeholder = placeholder,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ReviewScore(
                rating = savedRecipe.rating,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(
                        if (shouldShowRecipeMetadata) 0.5f else 1f
                    )
                ) {
                    Text(
                        text = savedRecipe.title,
                        style = AppTextStyles.smallTextBold,
                        color = AppColors.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = "By ${savedRecipe.authorName}",
                        style = AppTextStyles.smallerTextSmallLabel.copy(
                            fontSize = 8.sp
                        ),
                        color = AppColors.Gray3,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (shouldShowRecipeMetadata) {
                    RecipeMetaData(
                        isBookmarked = savedRecipe.isBookmarked,
                        cookingMinute = savedRecipe.cookingMinute
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeMetaData(
    isBookmarked: Boolean,
    cookingMinute: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = CraIcons.alarm,
            contentDescription = null,
            tint = AppColors.Gray4
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = "$cookingMinute min",
            style = AppTextStyles.smallerTextRegular,
            color = AppColors.Gray4,
        )
        Spacer(Modifier.width(10.dp))
        IconToggleButton(
            modifier = Modifier.size(24.dp),
            onCheckedChange = { },
            checked = isBookmarked,
            icon = {
                Icon(
                    imageVector = CraIcons.bookMarkBorder,
                    contentDescription = null
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = CraIcons.bookMark,
                    contentDescription = null
                )
            },
        )
    }
}

@Preview
@Composable
private fun RecipeCardPreview() {
    ComposeRecipeAppTheme {
        RecipeCard(
            fakeSavedRecipe[0],
            contentDescription = null,
            modifier = Modifier.aspectRatio(1f)
        )
    }
}
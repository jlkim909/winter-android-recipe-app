package com.surivalcoding.composerecipeapp.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.surivalcoding.composerecipeapp.ui.custom.HoButton
import com.surivalcoding.composerecipeapp.ui.theme.ComposeRecipeAppTheme

@Composable
fun MediumButton(
    text: String,
    onClick: () -> Unit,
    isPressed: Boolean = false,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    HoButton(
        modifier = modifier
            .wrapContentSize()
            .width(243.dp)
            .heightIn(min = 54.dp),
        onClick = onClick,
        isPressed = isPressed,
        enabled = enabled,
        isTrailingIcon = true,
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(min = 114.dp)
        )
    }
}

@Preview
@Composable
private fun MediumButtonPreview() {
    ComposeRecipeAppTheme {
        MediumButton(
            onClick = {},
            text = "Button",
        )
    }
}
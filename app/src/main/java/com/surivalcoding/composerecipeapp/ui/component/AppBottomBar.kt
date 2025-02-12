package com.surivalcoding.composerecipeapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.surivalcoding.composerecipeapp.TopLevelDestination
import com.surivalcoding.composerecipeapp.ui.RecipeAppState
import com.surivalcoding.composerecipeapp.ui.icon.AppIcons
import com.surivalcoding.composerecipeapp.ui.theme.AppColors
import kotlin.reflect.KClass

@Composable
fun AppBottomBar(
    currentDestination: NavDestination?,
    appState: RecipeAppState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(120.dp)
            .background(Color.Transparent)
    ) {
        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            containerColor = AppColors.Primary100,
            shape = CircleShape,
            onClick = {},
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = AppIcons.Add,
                contentDescription = "add",
                modifier = Modifier.size(20.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = BarShape(
                        circleRadius = 28.dp,
                        cornerRadius = 0.dp,
                        circleGap = 12.dp
                    )
                )
                .align(Alignment.BottomStart)
        ) {
            NavigationBar(
                containerColor = AppColors.White,
                modifier = Modifier
                    .matchParentSize()
            ) {
                TopLevelDestination.entries.forEach { destination ->
                    val selected =
                        currentDestination.isRouteInHierarchy(destination.baseRoute)
                    NavigationBarItem(
                        selected = selected,
                        onClick = { appState.navigateToTopLevelDestination(destination) },
                        label = {},
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.name,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = AppColors.Primary100,
                            unselectedIconColor = AppColors.Gray4
                        )
                    )
                }
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } == true

private class BarShape(
    private val circleRadius: Dp,
    private val cornerRadius: Dp,
    private val circleGap: Dp = 5.dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val cutoutCenterX = size.width / 2
        val cutoutRadius = density.run { (circleRadius + circleGap).toPx() }
        val cornerRadiusPx = density.run { cornerRadius.toPx() }
        val cornerDiameter = cornerRadiusPx * 2
        return Path().apply {
            val cutoutEdgeOffset = cutoutRadius * 1.5f
            val cutoutLeftX = cutoutCenterX - cutoutEdgeOffset
            val cutoutRightX = cutoutCenterX + cutoutEdgeOffset

            moveTo(x = 0F, y = size.height)
            if (cutoutLeftX > 0) {
                val realLeftCornerDiameter = if (cutoutLeftX >= cornerRadiusPx) {
                    cornerDiameter
                } else {
                    cutoutLeftX * 2
                }
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = realLeftCornerDiameter,
                        bottom = realLeftCornerDiameter
                    ),
                    startAngleDegrees = 180.0f,
                    sweepAngleDegrees = 90.0f,
                    forceMoveTo = false
                )
            }
            lineTo(cutoutLeftX, 0f)
            cubicTo(
                x1 = cutoutCenterX - cutoutRadius + 20,
                y1 = 0f,
                x2 = cutoutCenterX - cutoutRadius + 20,
                y2 = cutoutRadius,
                x3 = cutoutCenterX,
                y3 = cutoutRadius,
            )
            cubicTo(
                x1 = cutoutCenterX + cutoutRadius - 20,
                y1 = cutoutRadius,
                x2 = cutoutCenterX + cutoutRadius - 20,
                y2 = 0f,
                x3 = cutoutRightX,
                y3 = 0f,
            )
            if (cutoutRightX < size.width) {
                val realRightCornerDiameter = if (cutoutRightX <= size.width - cornerRadiusPx) {
                    cornerDiameter
                } else {
                    (size.width - cutoutRightX) * 2
                }
                arcTo(
                    rect = Rect(
                        left = size.width - realRightCornerDiameter,
                        top = 0f,
                        right = size.width,
                        bottom = realRightCornerDiameter
                    ),
                    startAngleDegrees = -90.0f,
                    sweepAngleDegrees = 90.0f,
                    forceMoveTo = false
                )
            }
            lineTo(x = size.width, y = size.height)
            close()
        }
    }
}
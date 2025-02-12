package com.surivalcoding.composerecipeapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.surivalcoding.composerecipeapp.ui.custom.HoTab
import com.surivalcoding.composerecipeapp.ui.custom.HoTabRow
import org.junit.Rule
import org.junit.Test

class HoTabsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_Tabs_DisplaysTabs() {
        val selectedTabIndex = mutableStateOf(0)
        val labels = listOf("Home", "Profile", "Settings")

        composeTestRule.setContent {
            HoTabRow {
                labels.forEachIndexed { index, label ->
                    HoTab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        label = label
                    )
                }
            }
        }

        labels.forEach { label ->
            composeTestRule.onNodeWithText(label).assertExists()
        }
    }

    @Test
    fun test_Tab_SelectionChangesColor() {
        val selectedTabIndex = mutableStateOf(0)
        val labels = listOf("Tab1", "Tab2", "Tab3")

        composeTestRule.setContent {
            HoTabRow {
                labels.forEachIndexed { index, label ->
                    HoTab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        label = label
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Tab1").assertExists().assertIsSelected()
        composeTestRule.onNodeWithText("Tab2").assertExists().assertIsNotSelected()

        composeTestRule.onNodeWithText("Tab2").performClick()

        composeTestRule.onNodeWithText("Tab1").assertIsNotSelected()
        composeTestRule.onNodeWithText("Tab2").assertIsSelected()
    }
}

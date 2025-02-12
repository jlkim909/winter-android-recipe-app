package com.surivalcoding.composerecipeapp.data.repository

import com.surivalcoding.composerecipeapp.data.model.SearchFilterOptions
import com.surivalcoding.composerecipeapp.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    fun setRecipeBookmarked(id: Int, bookmarked: Boolean)
    fun setSearchFilterOptions(searchFilterOptions: SearchFilterOptions)
    fun setRecentQuery(query: String)
}
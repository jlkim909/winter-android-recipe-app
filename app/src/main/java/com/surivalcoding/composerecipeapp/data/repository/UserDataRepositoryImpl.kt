package com.surivalcoding.composerecipeapp.data.repository

import com.surivalcoding.composerecipeapp.data.datasource.PreferenceDataSource
import com.surivalcoding.composerecipeapp.data.model.SearchFilterOptions
import com.surivalcoding.composerecipeapp.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    preferenceDataSource: PreferenceDataSource
) : UserDataRepository {
    private val _userData: MutableStateFlow<UserData> =
        MutableStateFlow(preferenceDataSource.userData)

    override val userData: Flow<UserData> = _userData
    override fun setRecipeBookmarked(id: Int, bookmarked: Boolean) {
        _userData.update {
            it.copy(
                bookmarkIds = if (bookmarked) it.bookmarkIds + id else it.bookmarkIds - id
            )
        }
    }

    override fun setSearchFilterOptions(searchFilterOptions: SearchFilterOptions) {
        _userData.update {
            it.copy(
                searchFilterOptions = searchFilterOptions
            )
        }
    }

    override fun setRecentQuery(query: String) {
        _userData.update {
            it.copy(
                recentQuery = query
            )
        }
    }
}
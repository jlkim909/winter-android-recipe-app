package com.surivalcoding.composerecipeapp.data.datasource

import com.surivalcoding.composerecipeapp.data.mock.fakeSearchRecipe
import com.surivalcoding.composerecipeapp.data.model.SearchRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecentSearchRecipeRepositoryImpl() : RecentSearchRecipeRepository {
    override fun getRecentSearchRecipe(): Flow<List<SearchRecipe>> =
        flowOf(fakeSearchRecipe)
}
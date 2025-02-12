package com.surivalcoding.composerecipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surivalcoding.composerecipeapp.common.result.Result
import com.surivalcoding.composerecipeapp.common.result.asResult
import com.surivalcoding.composerecipeapp.data.mapper.toHomeRecipe
import com.surivalcoding.composerecipeapp.data.mapper.toNewRecipe
import com.surivalcoding.composerecipeapp.data.model.HomeRecipe
import com.surivalcoding.composerecipeapp.data.model.NewRecipe
import com.surivalcoding.composerecipeapp.data.model.RecipeCategory
import com.surivalcoding.composerecipeapp.data.repository.RecipeRepository
import com.surivalcoding.composerecipeapp.data.repository.UserDataRepository
import com.surivalcoding.composerecipeapp.presentation.home.HomeAction.UpdateCategory
import com.surivalcoding.composerecipeapp.presentation.home.HomeAction.UpdateUserBookMarked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    recipeRepository: RecipeRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    private val _selectedCategory = MutableStateFlow(RecipeCategory.ALL)

    private val _actions = MutableSharedFlow<HomeAction>()
    private val actions = _actions.asSharedFlow()

    val homeUiState: StateFlow<HomeUiState> = homeUiState(
        _selectedCategory,
        userDataRepository,
        recipeRepository
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState.Loading
        )

    init {
        viewModelScope.launch {
            actions.collect {
                handleAction(it)
            }
        }
    }

    fun setAction(action: HomeAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun handleAction(action: HomeAction) {
        when (action) {
            is UpdateCategory -> {
                _selectedCategory.update {
                    action.category
                }
            }

            is UpdateUserBookMarked -> {
                userDataRepository.setRecipeBookmarked(action.id, action.bookmarked)
            }
        }
    }
}

private fun homeUiState(
    selectedCategory: StateFlow<RecipeCategory>,
    userDataRepository: UserDataRepository,
    recipeRepository: RecipeRepository,
): Flow<HomeUiState> {
    return combine(
        selectedCategory,
        userDataRepository.userData,
        recipeRepository.getRecipes(),
        ::Triple
    )
        .asResult()
        .map { homeUiResult ->
            when (homeUiResult) {
                is Result.Success -> {
                    val (selectedCategory, userData, recipes) = homeUiResult.data
                    val category = selectedCategory
                    val homeRecipes = (if (category == RecipeCategory.ALL) recipes
                    else recipes.filter { it.category == category }).map { it.toHomeRecipe() }
                    HomeUiState.Loaded(
                        selectedCategory = category,
                        bookmarkedIds = userData.bookmarkIds,
                        homeRecipes = homeRecipes,
                        newRecipes = recipes.map { it.toNewRecipe() }
                    )
                }

                is Result.Error -> HomeUiState.Error
                is Result.Loading -> HomeUiState.Loading
            }
        }
}

sealed interface HomeUiState {
    data object Error : HomeUiState
    data object Loading : HomeUiState
    data class Loaded(
        val selectedCategory: RecipeCategory = RecipeCategory.ALL,
        val bookmarkedIds: Set<Int>,
        val homeRecipes: List<HomeRecipe>,
        val newRecipes: List<NewRecipe>,
    ) : HomeUiState
}

sealed interface HomeAction {
    data class UpdateCategory(val category: RecipeCategory) : HomeAction
    data class UpdateUserBookMarked(val id: Int, val bookmarked: Boolean) : HomeAction
}
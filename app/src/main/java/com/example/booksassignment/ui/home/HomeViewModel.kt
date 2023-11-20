package com.example.booksassignment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.repositories.bookslist.BooksListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksListRepository: BooksListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadBooksLists()
    }

    private fun loadBooksLists() {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch {
            when (val result = booksListRepository.getAll()) {
                is CustomResult.Success -> {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            isLoading = false,
                            booksLists = result.data
                        )
                    }
                }
                is CustomResult.Error -> {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(isLoading = false)
                    }
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val booksLists: List<BooksList> = listOf()
)

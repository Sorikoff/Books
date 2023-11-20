package com.example.booksassignment.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.repositories.book.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BooksUiState())
    val uiState: StateFlow<BooksUiState> = _uiState

    fun loadBooksByListId(id: Int) {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch {
            when (val result = bookRepository.getByListId(id)) {
                is CustomResult.Success -> {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            isLoading = false,
                            books = result.data
                        )
                    }
                }
                is CustomResult.Error -> {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.exception.localizedMessage
                        )
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isError = false,
                errorMessage = null
            )
        }
    }
}

data class BooksUiState(
    val isLoading: Boolean = false,

    val isError: Boolean = false,
    val errorMessage: String? = null,

    val books: List<Book> = listOf()
)

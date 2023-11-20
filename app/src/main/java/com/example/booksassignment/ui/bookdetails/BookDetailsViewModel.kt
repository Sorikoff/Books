package com.example.booksassignment.ui.bookdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.models.BookDetails
import com.example.booksassignment.data.repositories.bookdetails.BookDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val bookDetailsRepository: BookDetailsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookDetailsUiState())
    val uiState: StateFlow<BookDetailsUiState> = _uiState

    fun loadBookDetailsByBookId(id: Int) {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch {
            when (val result = bookDetailsRepository.getByBookId(id)) {
                is CustomResult.Success -> {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            isLoading = false,
                            bookDetails = result.data
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

data class BookDetailsUiState(
    val isLoading: Boolean = false,

    val isError: Boolean = false,
    val errorMessage: String? = null,

    val bookDetails: BookDetails? = null
)

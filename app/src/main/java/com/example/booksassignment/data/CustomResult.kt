package com.example.booksassignment.data

/**
 * A generic class that holds a value or exception.
 * @param <T>
 */
sealed class CustomResult<out T> {
    /**
     * Represents completed request with response data.
     */
    data class Success<T>(val data: T) : CustomResult<T>()

    /**
     * Represents failed request with exception thrown.
     */
    data class Error(val exception: Exception) : CustomResult<Nothing>()
}

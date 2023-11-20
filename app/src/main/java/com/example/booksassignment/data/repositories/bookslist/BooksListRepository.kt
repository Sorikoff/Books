package com.example.booksassignment.data.repositories.bookslist

import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.models.BooksList

interface BooksListRepository {
    /**
     * Get books lists entry with up to 5 books associated with each entry.
     * @param forceFetch Set to true to force fetch from network, which will also invalidate cache.
     */
    suspend fun getAll(forceFetch: Boolean = false): CustomResult<List<BooksList>>
}

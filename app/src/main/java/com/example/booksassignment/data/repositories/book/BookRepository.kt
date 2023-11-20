package com.example.booksassignment.data.repositories.book

import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.models.Book

interface BookRepository {
    /**
     * Get all books belonging to the list.
     * @param id ID of the books list.
     * @param forceFetch Set to true to force fetch from network, which will also invalidate cache.
     */
    suspend fun getByListId(id: Int, forceFetch: Boolean = false): CustomResult<List<Book>>
}

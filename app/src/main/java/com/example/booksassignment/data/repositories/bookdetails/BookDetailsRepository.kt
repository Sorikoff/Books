package com.example.booksassignment.data.repositories.bookdetails

import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.models.BookDetails

interface BookDetailsRepository {
    /**
     * Get book details.
     * @param id ID of the book.
     * @param forceFetch Set to true to force fetch from network, which will also invalidate cache.
     */
    suspend fun getByBookId(id: Int, forceFetch: Boolean = false): CustomResult<BookDetails?>
}

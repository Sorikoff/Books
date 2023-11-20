package com.example.booksassignment.data.repositories

import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.sources.remote.models.BookModel
import com.example.booksassignment.data.sources.remote.models.BookDetailsModel
import com.example.booksassignment.data.sources.remote.models.BooksListModel

interface BooksRepository {

    suspend fun getBooksLists(): CustomResult<List<BooksListModel>>

    suspend fun getBooks(): CustomResult<List<BookModel>>

    suspend fun getBook(id: Int): CustomResult<BookDetailsModel>
}

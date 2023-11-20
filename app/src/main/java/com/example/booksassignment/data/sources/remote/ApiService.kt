package com.example.booksassignment.data.sources.remote

import com.example.booksassignment.data.sources.remote.book.BookModel
import com.example.booksassignment.data.sources.remote.bookdetails.BookDetailsModel
import com.example.booksassignment.data.sources.remote.bookslist.BooksListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/book/{id}")
    suspend fun getBook(@Path("id") id: Int): Response<BookDetailsModel>

    @GET("/books")
    suspend fun getBooks(): Response<List<BookModel>>

    @GET("/lists")
    suspend fun getBooksLists(): Response<List<BooksListModel>>
}

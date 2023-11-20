package com.example.booksassignment.data.mappings.bookslist

import com.example.booksassignment.data.mappings.DtoListMapper
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.sources.remote.bookslist.BooksListModel
import javax.inject.Inject

class NetworkBooksListMapper @Inject constructor() : DtoListMapper<BooksListModel, BooksList> {

    override fun map(input: List<BooksListModel>): List<BooksList> {
        return input.map { book ->
            BooksList(
                id = book.id,
                title = book.title,
                books = listOf()
            )
        }
    }
}

package com.example.booksassignment.data.mappings.book

import com.example.booksassignment.data.mappings.DtoListMapper
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.sources.remote.book.BookModel
import javax.inject.Inject

class NetworkBookMapper @Inject constructor() : DtoListMapper<BookModel, Book> {

    override fun map(input: List<BookModel>): List<Book> {
        return input.map { book ->
            Book(
                id = book.id,
                listId = book.listId,
                title = book.title,
                img = book.img
            )
        }
    }
}

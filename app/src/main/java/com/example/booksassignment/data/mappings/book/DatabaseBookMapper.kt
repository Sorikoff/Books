package com.example.booksassignment.data.mappings.book

import com.example.booksassignment.data.mappings.DtoListMapper
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.sources.local.book.BookEntity
import javax.inject.Inject

class DatabaseBookMapper @Inject constructor() : DtoListMapper<BookEntity, Book> {

    override fun map(input: List<BookEntity>): List<Book> {
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

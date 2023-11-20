package com.example.booksassignment.data.mappings.book

import com.example.booksassignment.data.mappings.DtoListMapper
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.sources.local.book.BookEntity
import java.time.OffsetDateTime
import javax.inject.Inject

class BookDatabaseMapper @Inject constructor() : DtoListMapper<Book, BookEntity> {

    override fun map(input: List<Book>): List<BookEntity> {
        return input.map { book ->
            BookEntity(
                id = book.id,
                listId = book.listId,
                title = book.title,
                img = book.img,
                createdAt = OffsetDateTime.now()
            )
        }
    }
}
package com.example.booksassignment.data.mappings.book

import com.example.booksassignment.data.mappings.DtoMapper
import com.example.booksassignment.data.mappings.bookdetails.DatabaseBookDetailsMapper
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.sources.local.book.BookEntity
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsEntity
import javax.inject.Inject

class DatabaseBookBookDetailsMapper @Inject constructor(
    private val databaseBookDetailsMapper: DatabaseBookDetailsMapper
) : DtoMapper<Map<BookEntity, List<BookDetailsEntity>>, List<Book>> {

    override fun map(input: Map<BookEntity, List<BookDetailsEntity>>): List<Book> {
        return input.map { book ->
            Book(
                id = book.key.id,
                listId = book.key.listId,
                title = book.key.title,
                img = book.key.img,
                details = book.value.firstOrNull()?.let { details ->
                    databaseBookDetailsMapper.map(details)
                }
            )
        }
    }
}

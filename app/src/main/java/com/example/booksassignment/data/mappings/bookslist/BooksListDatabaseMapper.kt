package com.example.booksassignment.data.mappings.bookslist

import com.example.booksassignment.data.mappings.DtoListMapper
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.sources.local.bookslist.BooksListEntity
import java.time.OffsetDateTime
import javax.inject.Inject

class BooksListDatabaseMapper @Inject constructor() : DtoListMapper<BooksList, BooksListEntity> {

    override fun map(input: List<BooksList>): List<BooksListEntity> {
        return input.map { book ->
            BooksListEntity(
                id = book.id,
                title = book.title,
                createdAt = OffsetDateTime.now()
            )
        }
    }
}
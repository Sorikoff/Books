package com.example.booksassignment.data.mappings.bookslist

import com.example.booksassignment.data.mappings.DtoListMapper
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.sources.local.bookslist.BooksListEntity
import javax.inject.Inject

class DatabaseBooksListMapper @Inject constructor() : DtoListMapper<BooksListEntity, BooksList> {

    override fun map(input: List<BooksListEntity>): List<BooksList> {
        return input.map { book ->
            BooksList(
                id = book.id,
                title = book.title
            )
        }
    }
}

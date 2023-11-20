package com.example.booksassignment.data.mappings.bookslist

import com.example.booksassignment.data.mappings.DtoMapper
import com.example.booksassignment.data.mappings.book.DatabaseBookMapper
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.sources.local.book.BookEntity
import com.example.booksassignment.data.sources.local.bookslist.BooksListEntity
import javax.inject.Inject

class DatabaseBooksListMapper @Inject constructor(
    private val databaseBookMapper: DatabaseBookMapper
) : DtoMapper<Map<BooksListEntity, List<BookEntity>>, List<BooksList>> {

    override fun map(input: Map<BooksListEntity, List<BookEntity>>): List<BooksList> {
        return input.map { book ->
            BooksList(
                id = book.key.id,
                title = book.key.title,
                books = databaseBookMapper.map(book.value)
            )
        }
    }
}

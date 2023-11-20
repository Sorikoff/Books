package com.example.booksassignment.data.mappings.bookdetails

import com.example.booksassignment.data.mappings.DtoMapper
import com.example.booksassignment.data.models.BookDetails
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsEntity
import java.time.OffsetDateTime
import javax.inject.Inject

class BookDetailsDatabaseMapper @Inject constructor() : DtoMapper<BookDetails, BookDetailsEntity> {

    override fun map(input: BookDetails): BookDetailsEntity {
        return BookDetailsEntity(
            id = input.id,
            listId = input.listId,
            isbn = input.isbn,
            publicationDate = input.publicationDate,
            author = input.author,
            title = input.title,
            img = input.img,
            description = input.description,
            createdAt = OffsetDateTime.now()
        )
    }
}
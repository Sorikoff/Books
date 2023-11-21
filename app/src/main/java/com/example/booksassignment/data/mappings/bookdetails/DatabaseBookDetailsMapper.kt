package com.example.booksassignment.data.mappings.bookdetails

import com.example.booksassignment.data.mappings.DtoMapper
import com.example.booksassignment.data.models.BookDetails
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsEntity
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

class DatabaseBookDetailsMapper @Inject constructor() : DtoMapper<BookDetailsEntity, BookDetails> {

    override fun map(input: BookDetailsEntity): BookDetails {
        return BookDetails(
            id = input.id,
            listId = input.listId,
            isbn = input.isbn,
            publicationDate = getFormattedPublicationDate(input.publicationDate),
            author = input.author,
            title = input.title,
            img = input.img,
            description = input.description
        )
    }

    private fun getFormattedPublicationDate(publicationDate: String): String {
        try {
            val offsetDateTime = OffsetDateTime.parse(publicationDate)
            return offsetDateTime.format(DateTimeFormatter.ISO_DATE)
        } catch (_: DateTimeParseException) {
            return publicationDate
        }
    }
}

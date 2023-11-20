package com.example.booksassignment.data.mappings.bookdetails

import com.example.booksassignment.data.mappings.DtoMapper
import com.example.booksassignment.data.models.BookDetails
import com.example.booksassignment.data.sources.remote.bookdetails.BookDetailsModel
import javax.inject.Inject

class NetworkBookDetailsMapper @Inject constructor() : DtoMapper<BookDetailsModel, BookDetails> {

    override fun map(input: BookDetailsModel): BookDetails {
        return BookDetails(
            id = input.id,
            listId = input.listId,
            isbn = input.isbn ?: "",
            publicationDate = input.publicationDate ?: "",
            author = input.author,
            title = input.title,
            img = input.img,
            description = input.description
        )
    }
}

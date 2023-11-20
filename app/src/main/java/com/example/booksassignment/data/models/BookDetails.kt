package com.example.booksassignment.data.models

data class BookDetails(
    val id: Int,
    val listId: Int,
    val isbn: String,
    val publicationDate: String,
    val author: String,
    val title: String,
    val img: String,
    val description: String
)

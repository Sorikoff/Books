package com.example.booksassignment.data.sources.remote.bookdetails

import com.google.gson.annotations.SerializedName

data class BookDetailsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("list_id")
    val listId: Int,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("description")
    val description: String
)

package com.example.booksassignment.data.sources.remote.models

import com.google.gson.annotations.SerializedName

data class BooksListModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)

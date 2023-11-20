package com.example.booksassignment.data.sources.remote.models

import com.google.gson.annotations.SerializedName

data class BookModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("list_id")
    val listId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("img")
    val img: String
)

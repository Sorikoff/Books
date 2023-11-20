package com.example.booksassignment.data.sources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(
    tableName = "BookDetails",
    foreignKeys = [ForeignKey(
        entity = BooksListEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("list_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class BookDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "list_id", index = true)
    val listId: Int,
    @ColumnInfo(name = "isbn")
    val isbn: String,
    @ColumnInfo(name = "publication_date")
    val publicationDate: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "img")
    val img: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "created_at")
    var createdAt: OffsetDateTime? = null
)

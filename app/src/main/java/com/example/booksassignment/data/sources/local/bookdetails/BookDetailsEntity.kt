package com.example.booksassignment.data.sources.local.bookdetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.booksassignment.data.sources.local.bookslist.BooksListEntity
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
    var id: Int,
    @ColumnInfo(name = "list_id", index = true)
    var listId: Int,
    @ColumnInfo(name = "isbn")
    var isbn: String,
    @ColumnInfo(name = "publication_date")
    var publicationDate: String,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "img")
    var img: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "created_at")
    var createdAt: OffsetDateTime? = null
)

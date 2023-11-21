package com.example.booksassignment.data.sources.local.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsEntity

@Dao
interface BookDao {

    @Query(
        "SELECT * FROM Book " +
        "LEFT JOIN BookDetails ON Book.id = BookDetails.id WHERE Book.list_id = :listId"
    )
    suspend fun getByListIdWithDetails(listId: Int): Map<BookEntity, List<BookDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(books: List<BookEntity>)

    @Query("DELETE FROM Book")
    suspend fun delete()
}

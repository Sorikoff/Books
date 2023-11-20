package com.example.booksassignment.data.sources.local.bookslist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksassignment.data.sources.local.book.BookEntity

@Dao
interface BooksListDao {

    @Query(
        "SELECT * FROM BooksList AS bl " +
        "JOIN Book AS b ON b.id IN " +
        "(SELECT id FROM Book AS b2 " +
        "WHERE b2.list_id = bl.id " +
        "ORDER BY id LIMIT 5)"
    )
    suspend fun getAllWithBooks(): Map<BooksListEntity, List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(books: List<BooksListEntity>)

    @Query("DELETE FROM BooksList")
    suspend fun delete()
}

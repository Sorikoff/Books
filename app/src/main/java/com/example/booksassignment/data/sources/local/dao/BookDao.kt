package com.example.booksassignment.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksassignment.data.sources.local.entities.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(books: List<BookEntity>)

    @Query("DELETE FROM Book")
    suspend fun delete()

    @Query("SELECT * FROM Book")
    suspend fun getAll(): List<BookEntity>
}

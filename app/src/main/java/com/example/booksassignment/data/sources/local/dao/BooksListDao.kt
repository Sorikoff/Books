package com.example.booksassignment.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksassignment.data.sources.local.entities.BooksListEntity

@Dao
interface BooksListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(books: List<BooksListEntity>)

    @Query("DELETE FROM BooksList")
    suspend fun delete()

    @Query("SELECT * FROM BooksList")
    suspend fun getAll(): List<BooksListEntity>
}

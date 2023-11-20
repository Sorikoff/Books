package com.example.booksassignment.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksassignment.data.sources.local.entities.BookDetailsEntity

@Dao
interface BookDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(books: List<BookDetailsEntity>)

    @Query("DELETE FROM BookDetails")
    suspend fun delete()

    @Query("SELECT * FROM BookDetails")
    suspend fun getAll(): List<BookDetailsEntity>
}

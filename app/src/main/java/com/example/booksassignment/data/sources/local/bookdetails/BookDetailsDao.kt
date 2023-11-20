package com.example.booksassignment.data.sources.local.bookdetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDetailsDao {

    @Query("SELECT * FROM BookDetails WHERE id = :id")
    suspend fun getById(id: Int): List<BookDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: BookDetailsEntity)

    @Query("DELETE FROM BookDetails")
    suspend fun delete()
}

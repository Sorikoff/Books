package com.example.booksassignment.data.sources.local.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * FROM Book WHERE list_id = :listId")
    suspend fun getByListId(listId: Int): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(books: List<BookEntity>)

    @Query("DELETE FROM Book")
    suspend fun delete()
}

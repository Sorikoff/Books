package com.example.booksassignment.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.booksassignment.data.sources.local.dao.BookDao
import com.example.booksassignment.data.sources.local.dao.BookDetailsDao
import com.example.booksassignment.data.sources.local.dao.BooksListDao
import com.example.booksassignment.data.sources.local.entities.BookDetailsEntity
import com.example.booksassignment.data.sources.local.entities.BookEntity
import com.example.booksassignment.data.sources.local.entities.BooksListEntity

@Database(
    entities = [
        BookDetailsEntity::class,
        BookEntity::class,
        BooksListEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun bookDetailsDao(): BookDetailsDao

    abstract fun booksListDao(): BooksListDao
}

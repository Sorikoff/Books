package com.example.booksassignment.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.booksassignment.data.sources.local.book.BookDao
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsDao
import com.example.booksassignment.data.sources.local.bookslist.BooksListDao
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsEntity
import com.example.booksassignment.data.sources.local.book.BookEntity
import com.example.booksassignment.data.sources.local.bookslist.BooksListEntity

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

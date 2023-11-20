package com.example.booksassignment.di

import com.example.booksassignment.data.repositories.book.BookRepository
import com.example.booksassignment.data.repositories.book.DefaultBookRepository
import com.example.booksassignment.data.repositories.bookdetails.BookDetailsRepository
import com.example.booksassignment.data.repositories.bookdetails.DefaultBookDetailsRepository
import com.example.booksassignment.data.repositories.bookslist.BooksListRepository
import com.example.booksassignment.data.repositories.bookslist.DefaultBooksListRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Reusable
    fun bindBookRepository(repository: DefaultBookRepository): BookRepository

    @Binds
    @Reusable
    fun bindBookDetailsRepository(repository: DefaultBookDetailsRepository): BookDetailsRepository

    @Binds
    @Reusable
    fun bindBooksListRepository(repository: DefaultBooksListRepository): BooksListRepository
}

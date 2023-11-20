package com.example.booksassignment.di

import android.content.Context
import androidx.room.Room
import com.example.booksassignment.Constants
import com.example.booksassignment.data.sources.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBookDao(database: AppDatabase) = database.bookDao()

    @Provides
    @Singleton
    fun provideBookDetailsDao(database: AppDatabase) = database.bookDetailsDao()

    @Provides
    @Singleton
    fun provideBooksListDao(database: AppDatabase) = database.booksListDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
}

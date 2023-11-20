package com.example.booksassignment.data.repositories.book

import com.example.booksassignment.Constants
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.mappings.book.BookDatabaseMapper
import com.example.booksassignment.data.mappings.book.DatabaseBookMapper
import com.example.booksassignment.data.mappings.book.NetworkBookMapper
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.repositories.executeApi
import com.example.booksassignment.data.repositories.executeDatabase
import com.example.booksassignment.data.sources.local.book.BookDao
import com.example.booksassignment.data.sources.remote.ApiService
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class DefaultBookRepository @Inject constructor(
    // Remote
    private val apiService: ApiService,
    // Local
    private val bookDao: BookDao,
    // Mappings
    private val bookDatabaseMapper: BookDatabaseMapper,
    private val databaseBookMapper: DatabaseBookMapper,
    private val networkBookMapper: NetworkBookMapper
) : BookRepository {

    override suspend fun getByListId(id: Int, forceFetch: Boolean): CustomResult<List<Book>> {
        if (!forceFetch) {
            val databaseBooks = getByListIdFromDatabase(id)
            if (databaseBooks.isNotEmpty()) {
                return CustomResult.Success(databaseBooks)
            }
        }

        val networkResult = executeApi {
            apiService.getBooks()
        }
        return when (networkResult) {
            is CustomResult.Success -> {
                val data = networkBookMapper.map(networkResult.data)
                bookDao.delete()
                bookDao.insert(bookDatabaseMapper.map(data))

                // Save all data but return only subset required.
                val databaseBooks = getByListIdFromDatabase(id)
                CustomResult.Success(databaseBooks)
            }

            is CustomResult.Error -> networkResult
        }
    }

    private suspend fun getByListIdFromDatabase(id: Int): List<Book> {
        val result = executeDatabase {
            bookDao.getByListId(id)
        }
        return when (result) {
            is CustomResult.Success -> {
                val results = result.data
                if (results.isEmpty()) {
                    return listOf()
                }

                val now = OffsetDateTime.now()
                val diff = ChronoUnit.SECONDS.between(
                    results.last().createdAt,
                    now
                )

                if (diff < Constants.CACHE_1_HOUR) {
                    databaseBookMapper.map(results)
                } else {
                    listOf()
                }
            }

            is CustomResult.Error -> listOf()
        }
    }
}

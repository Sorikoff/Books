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
            val databaseResult = executeDatabase {
                bookDao.getByListId(id)
            }
            val databaseBooks = when (databaseResult) {
                is CustomResult.Success -> databaseResult.data
                is CustomResult.Error -> listOf()
            }

            if (databaseBooks.isNotEmpty()) {
                val now = OffsetDateTime.now()
                val diff = ChronoUnit.SECONDS.between(databaseBooks.last().createdAt, now)
                if (diff < Constants.CACHE_1_HOUR) {
                    return CustomResult.Success(databaseBookMapper.map(databaseBooks))
                }
            }
        }

        val networkResult = executeApi {
            apiService.getBooks()
        }
        return when (networkResult) {
            is CustomResult.Success -> {
                val data = networkBookMapper.map(networkResult.data)
                bookDao.delete()
                bookDao.create(bookDatabaseMapper.map(data))
                // Save all data but return only subset required.
                val filtered = data.filter { it.listId == id }
                CustomResult.Success(filtered)
            }
            is CustomResult.Error -> networkResult
        }
    }
}

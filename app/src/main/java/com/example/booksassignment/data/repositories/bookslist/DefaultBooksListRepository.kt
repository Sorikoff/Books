package com.example.booksassignment.data.repositories.bookslist

import com.example.booksassignment.Constants
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.mappings.bookslist.BooksListDatabaseMapper
import com.example.booksassignment.data.mappings.bookslist.DatabaseBooksListMapper
import com.example.booksassignment.data.mappings.bookslist.NetworkBooksListMapper
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.repositories.executeApi
import com.example.booksassignment.data.repositories.executeDatabase
import com.example.booksassignment.data.sources.local.bookslist.BooksListDao
import com.example.booksassignment.data.sources.remote.ApiService
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class DefaultBooksListRepository @Inject constructor(
    // Remote
    private val apiService: ApiService,
    // Local
    private val booksListDao: BooksListDao,
    // Mappings
    private val booksListDatabaseMapper: BooksListDatabaseMapper,
    private val databaseBooksListMapper: DatabaseBooksListMapper,
    private val networkBooksListMapper: NetworkBooksListMapper
) : BooksListRepository {

    override suspend fun getAll(forceFetch: Boolean): CustomResult<List<BooksList>> {
        if (!forceFetch) {
            val databaseResult = executeDatabase {
                booksListDao.getAll()
            }
            val databaseBooksLists = when (databaseResult) {
                is CustomResult.Success -> databaseResult.data
                is CustomResult.Error -> listOf()
            }

            if (databaseBooksLists.isNotEmpty()) {
                val now = OffsetDateTime.now()
                val diff = ChronoUnit.SECONDS.between(databaseBooksLists.last().createdAt, now)
                if (diff < Constants.CACHE_1_HOUR) {
                    return CustomResult.Success(databaseBooksListMapper.map(databaseBooksLists))
                }
            }
        }

        val networkResult = executeApi {
            apiService.getBooksLists()
        }
        return when (networkResult) {
            is CustomResult.Success -> {
                val data = networkBooksListMapper.map(networkResult.data)
                booksListDao.delete()
                booksListDao.create(booksListDatabaseMapper.map(data))
                CustomResult.Success(data)
            }
            is CustomResult.Error -> networkResult
        }
    }
}

package com.example.booksassignment.data.repositories.bookslist

import com.example.booksassignment.Constants
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.mappings.book.BookDatabaseMapper
import com.example.booksassignment.data.mappings.book.NetworkBookMapper
import com.example.booksassignment.data.mappings.bookslist.BooksListDatabaseMapper
import com.example.booksassignment.data.mappings.bookslist.DatabaseBooksListMapper
import com.example.booksassignment.data.mappings.bookslist.NetworkBooksListMapper
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.data.repositories.executeApi
import com.example.booksassignment.data.repositories.executeDatabase
import com.example.booksassignment.data.sources.local.book.BookDao
import com.example.booksassignment.data.sources.local.bookslist.BooksListDao
import com.example.booksassignment.data.sources.remote.ApiService
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class DefaultBooksListRepository @Inject constructor(
    // Remote
    private val apiService: ApiService,
    // Local
    private val bookDao: BookDao,
    private val booksListDao: BooksListDao,
    // Mappings
    private val networkBookMapper: NetworkBookMapper,
    private val bookDatabaseMapper: BookDatabaseMapper,
    private val booksListDatabaseMapper: BooksListDatabaseMapper,
    private val databaseBooksListMapper: DatabaseBooksListMapper,
    private val networkBooksListMapper: NetworkBooksListMapper
) : BooksListRepository {

    override suspend fun getAll(forceFetch: Boolean): CustomResult<List<BooksList>> {
        if (!forceFetch) {
            val databaseBooksLists = getAllFromDatabase()
            if (databaseBooksLists.isNotEmpty()) {
                return CustomResult.Success(databaseBooksLists)
            }
        }

        when (val result = fetchAndSaveBookLists()) {
            is CustomResult.Success -> {
            }

            is CustomResult.Error -> return result
        }

        when (val result = fetchAndSaveBooks()) {
            is CustomResult.Success -> {
            }

            is CustomResult.Error -> return result
        }

        return CustomResult.Success(getAllFromDatabase())
    }

    private suspend fun getAllFromDatabase(): List<BooksList> {
        val result = executeDatabase {
            booksListDao.getAllWithBooks()
        }
        return when (result) {
            is CustomResult.Success -> {
                val now = OffsetDateTime.now()
                val diff = ChronoUnit.SECONDS.between(
                    result.data.keys.last().createdAt,
                    now
                )
                if (diff < Constants.CACHE_1_HOUR) {
                    databaseBooksListMapper.map(result.data)
                } else {
                    listOf()
                }
            }

            is CustomResult.Error -> listOf()
        }
    }

    private suspend fun fetchAndSaveBookLists(): CustomResult<Unit> {
        val networkResult = executeApi {
            apiService.getBooksLists()
        }
        return when (networkResult) {
            is CustomResult.Success -> {
                val networkData = networkBooksListMapper.map(networkResult.data)
                booksListDao.delete()
                booksListDao.insert(booksListDatabaseMapper.map(networkData))
                CustomResult.Success(Unit)
            }

            is CustomResult.Error -> networkResult
        }
    }

    private suspend fun fetchAndSaveBooks(): CustomResult<Unit> {
        val networkResult = executeApi {
            apiService.getBooks()
        }
        return when (networkResult) {
            is CustomResult.Success -> {
                val networkData = networkBookMapper.map(networkResult.data)
                bookDao.delete()
                bookDao.insert(bookDatabaseMapper.map(networkData))
                CustomResult.Success(Unit)
            }

            is CustomResult.Error -> networkResult
        }
    }
}

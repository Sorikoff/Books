package com.example.booksassignment.data.repositories.bookdetails

import com.example.booksassignment.Constants
import com.example.booksassignment.data.CustomResult
import com.example.booksassignment.data.mappings.bookdetails.BookDetailsDatabaseMapper
import com.example.booksassignment.data.mappings.bookdetails.DatabaseBookDetailsMapper
import com.example.booksassignment.data.mappings.bookdetails.NetworkBookDetailsMapper
import com.example.booksassignment.data.models.BookDetails
import com.example.booksassignment.data.repositories.executeApi
import com.example.booksassignment.data.repositories.executeDatabase
import com.example.booksassignment.data.sources.local.bookdetails.BookDetailsDao
import com.example.booksassignment.data.sources.remote.ApiService
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class DefaultBookDetailsRepository @Inject constructor(
    // Remote
    private val apiService: ApiService,
    // Local
    private val bookDetailsDao: BookDetailsDao,
    // Mappings
    private val bookDetailsDatabaseMapper: BookDetailsDatabaseMapper,
    private val databaseBookDetailsMapper: DatabaseBookDetailsMapper,
    private val networkBookDetailsMapper: NetworkBookDetailsMapper
) : BookDetailsRepository {

    override suspend fun getByBookId(id: Int, forceFetch: Boolean): CustomResult<BookDetails?> {
        if (!forceFetch) {
            val databaseBookDetails = getByBookIdFromDatabase(id)
            if (databaseBookDetails != null) {
                return CustomResult.Success(databaseBookDetails)
            }
        }

        val networkResult = executeApi {
            apiService.getBook(id)
        }
        return when (networkResult) {
            is CustomResult.Success -> {
                val data = networkBookDetailsMapper.map(networkResult.data)
                bookDetailsDao.deleteById(data.id)
                bookDetailsDao.insert(bookDetailsDatabaseMapper.map(data))

                val databaseBookDetails = getByBookIdFromDatabase(id)
                CustomResult.Success(databaseBookDetails)
            }

            is CustomResult.Error -> networkResult
        }
    }

    private suspend fun getByBookIdFromDatabase(id: Int): BookDetails? {
        val result = executeDatabase {
            bookDetailsDao.getById(id).firstOrNull()
        }
        return when (result) {
            is CustomResult.Success -> {
                val results = result.data ?: return null

                val now = OffsetDateTime.now()
                val diff = ChronoUnit.SECONDS.between(
                    results.createdAt,
                    now
                )

                if (diff < Constants.CACHE_1_HOUR) {
                    databaseBookDetailsMapper.map(results)
                } else {
                    null
                }
            }

            is CustomResult.Error -> null
        }
    }
}

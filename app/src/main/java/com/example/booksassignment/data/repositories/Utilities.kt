package com.example.booksassignment.data.repositories

import com.example.booksassignment.data.CustomResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Performs API request with coroutines and returns results wrapped in [CustomResult].
 * Catches [HttpException] and [IOException].
 *
 * @param callback Request to be executed.
 * @return Value as [CustomResult.Success] or exception as [CustomResult.Error].
 */
internal suspend inline fun <T> executeApi(crossinline callback: suspend () -> Response<T>): CustomResult<T> {
    return try {
        val response = callback()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            CustomResult.Success(body)
        } else {
            CustomResult.Error(HttpException(response))
        }
    } catch (exception: HttpException) {
        CustomResult.Error(exception)
    } catch (exception: IOException) {
        CustomResult.Error(exception)
    }
}

/**
 * Performs database request with coroutines and returns results wrapped in [CustomResult].
 * Catches [IOException].
 *
 * @param callback Request to be executed.
 * @return Value as [CustomResult.Success] or exception as [CustomResult.Error].
 */
internal suspend inline fun <T> executeDatabase(crossinline callback: suspend () -> T): CustomResult<T> {
    return try {
        val response = callback()
        CustomResult.Success(response)
    } catch (exception: IOException) {
        CustomResult.Error(exception)
    }
}

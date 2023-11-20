package com.example.booksassignment.data.mappings

/**
 * Maps data transfer objects to domain models and vice versa.
 * @param T Type of input objects.
 * @param R Type of output objects.
 */
interface DtoListMapper<T, R> {

    fun map(input: List<T>): List<R>
}
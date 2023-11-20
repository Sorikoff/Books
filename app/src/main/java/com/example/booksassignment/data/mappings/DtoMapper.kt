package com.example.booksassignment.data.mappings

/**
 * Maps data transfer object to domain model and vice versa.
 * @param T Type of input object.
 * @param R Type of output object.
 */
interface DtoMapper<T, R> {

    fun map(input: T): R
}

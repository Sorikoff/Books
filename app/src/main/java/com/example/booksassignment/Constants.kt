package com.example.booksassignment

object Constants {

    const val API_BASE_URL = "https://my-json-server.typicode.com/KeskoSenukaiDigital/assignment/"

    const val CACHE_1_HOUR = 60 * 60

    const val DATABASE_NAME = "AppDatabase"

    const val ROUTE_HOME = "ROUTE_HOME"
    const val ROUTE_LIST_ID = "ROUTE_BOOKS_LIST_ID"
    const val ROUTE_LIST_TITLE = "ROUTE_LIST_TITLE"
    const val ROUTE_BOOKS = "ROUTE_BOOKS"
    const val ROUTE_BOOKS_LIST_ID = "$ROUTE_BOOKS/{$ROUTE_LIST_ID}/{$ROUTE_LIST_TITLE}"
}

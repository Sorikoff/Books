package com.example.booksassignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booksassignment.Constants
import com.example.booksassignment.ui.bookdetails.BookDetailsScreen
import com.example.booksassignment.ui.books.BooksScreen
import com.example.booksassignment.ui.home.HomeScreen
import com.example.booksassignment.ui.theme.BooksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksApp()
        }
    }

    @Composable
    fun BooksApp() {
        val navController = rememberNavController()
        BooksTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavHost(navController = navController)
            }
        }
    }

    @Composable
    fun AppNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = Constants.ROUTE_HOME) {
            composable(
                route = Constants.ROUTE_HOME
            ) {
                HomeScreen(
                    navController = navController
                )
            }

            composable(
                route = Constants.ROUTE_BOOKS_ID_TITLE,
                arguments = listOf(
                    navArgument(Constants.ROUTE_LIST_ID) {
                        type = NavType.IntType
                    },
                    navArgument(Constants.ROUTE_LIST_TITLE) {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val listId = entry.arguments?.getInt(Constants.ROUTE_LIST_ID) ?: 1
                val listTitle = entry.arguments?.getString(Constants.ROUTE_LIST_TITLE) ?: ""
                BooksScreen(
                    listId = listId,
                    listTitle = listTitle,
                    navController = navController
                )
            }

            composable(
                route = Constants.ROUTE_BOOK_DETAILS_LIST_ID,
                arguments = listOf(
                    navArgument(Constants.ROUTE_BOOK_ID) {
                        type = NavType.IntType
                    }
                )
            ) { entry ->
                val bookId = entry.arguments?.getInt(Constants.ROUTE_BOOK_ID) ?: 1
                BookDetailsScreen(
                    bookId = bookId,
                    navController = navController
                )
            }
        }
    }
}

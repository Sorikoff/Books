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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booksassignment.Constants
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
            composable(Constants.ROUTE_HOME) {
                HomeScreen()
            }
        }
    }
}

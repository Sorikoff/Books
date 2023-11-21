package com.example.booksassignment.ui.books

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.booksassignment.Constants
import com.example.booksassignment.R
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.ui.theme.BlueWhite
import com.example.booksassignment.ui.theme.Typography
import com.example.booksassignment.ui.theme.horizontalPaddingModifier
import com.example.booksassignment.ui.widgets.BooksLoadingCircularView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BooksScreen(
    listId: Int,
    listTitle: String,
    navController: NavController,
    viewModel: BooksViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    if (uiState.isError) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = uiState.errorMessage ?: ""
            )
            viewModel.clearError()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadBooksByListId(listId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = listTitle,
                        style = Typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navController::navigateUp
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.image_back_button)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Box {
            val onRefresh = remember {
                {
                    viewModel.refresh(listId)
                }
            }
            val pullRefreshState = rememberPullRefreshState(
                refreshing = uiState.isRefreshing,
                onRefresh = onRefresh
            )
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .pullRefresh(pullRefreshState)
            ) {
                if (uiState.isLoading) {
                    BooksLoadingCircularView()
                } else {
                    Books(
                        books = uiState.books,
                        onItemClicked = { bookId ->
                            navController.navigate("${Constants.ROUTE_BOOK_DETAILS}/$bookId")
                        }
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = uiState.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun Books(
    books: List<Book>,
    onItemClicked: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = horizontalPaddingModifier
    ) {
        items(
            items = books,
            key = { item -> item.id }
        ) { book ->
            val onClick = remember {
                {
                    onItemClicked(book.id)
                }
            }
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clickable(onClick = onClick),
                border = BorderStroke(
                    width = 1.dp,
                    color = BlueWhite
                ),
                shape = ShapeDefaults.Small
            ) {
                Book(
                    title = book.title,
                    img = book.img
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Book(
    title: String,
    img: String
) {
    Row(
        modifier = horizontalPaddingModifier
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(
            model = img,
            contentDescription = stringResource(id = R.string.image_of_the_book),
            modifier = Modifier
                .size(width = 90.dp, height = 150.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            fontStyle = FontStyle.Italic,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = Typography.bodyLarge
        )
    }
}

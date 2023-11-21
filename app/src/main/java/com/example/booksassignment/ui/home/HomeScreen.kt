package com.example.booksassignment.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.booksassignment.Constants
import com.example.booksassignment.R
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.ui.theme.BlueWhite
import com.example.booksassignment.ui.theme.Typography
import com.example.booksassignment.ui.theme.horizontalPaddingModifier
import com.example.booksassignment.ui.widgets.BooksEmptyView
import com.example.booksassignment.ui.widgets.BooksLoadingCircularView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = Typography.titleLarge
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Box {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = uiState.isRefreshing,
                onRefresh = viewModel::refresh
            )
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .pullRefresh(pullRefreshState)
            ) {
                if (uiState.isLoading) {
                    BooksLoadingCircularView()
                } else {
                    if (uiState.booksLists.isEmpty()) {
                        BooksEmptyView(
                            textId = R.string.no_book_lists_found
                        )
                    } else {
                        BooksLists(
                            booksLists = uiState.booksLists,
                            onAllClicked = { listId, listTitle ->
                                navController.navigate("${Constants.ROUTE_BOOKS}/$listId/$listTitle")
                            }
                        )
                    }
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
fun BooksLists(
    booksLists: List<BooksList>,
    onAllClicked: (id: Int, title: String) -> Unit
) {
    LazyColumn(
        modifier = horizontalPaddingModifier
    ) {
        items(
            items = booksLists,
            key = { item -> item.id }
        ) { booksList ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = BlueWhite
                ),
                shape = ShapeDefaults.Small
            ) {
                BooksListTitle(
                    id = booksList.id,
                    title = booksList.title,
                    onAllClicked = onAllClicked
                )
                BooksListContent(
                    books = booksList.books
                )
            }
        }
    }
}

@Composable
fun BooksListTitle(
    id: Int,
    title: String,
    onAllClicked: (id: Int, title: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Typography.bodyLarge
        )
        val onClick = remember {
            {
                onAllClicked(id, title)
            }
        }
        TextButton(
            onClick = onClick,
            border = BorderStroke(1.dp, BlueWhite)
        ) {
            Text(
                text = stringResource(id = R.string.all),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

 @Composable
fun BooksListContent(
    books: List<Book>
) {
    LazyRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = books,
            key = { item -> item.id }
        ) { book ->
            Column(
                modifier = Modifier
                    .width(width = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(book.img)
                        .placeholder(
                            drawableResId = R.drawable.placeholder
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.image_of_the_book),
                    modifier = Modifier
                        .size(width = 120.dp, height = 200.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = book.title,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.bodyMedium
                )
            }
        }
    }
}

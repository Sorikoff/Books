package com.example.booksassignment.ui.bookdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.booksassignment.R
import com.example.booksassignment.ui.theme.Typography
import com.example.booksassignment.ui.theme.horizontalPaddingModifier
import com.example.booksassignment.ui.widgets.BooksEmptyView
import com.example.booksassignment.ui.widgets.BooksLoadingCircularView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BookDetailsScreen(
    bookId: Int,
    navController: NavController,
    viewModel: BookDetailsViewModel = hiltViewModel()
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
        viewModel.loadBookDetailsByBookId(bookId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.book),
                        style = Typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
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
                    viewModel.refresh(bookId)
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
                    if (uiState.bookDetails == null) {
                        BooksEmptyView(
                            textId = R.string.no_book_details_found
                        )
                    } else {
                        BookDetails(
                            img = uiState.bookDetails?.img ?: "",
                            title = uiState.bookDetails?.title ?: "",
                            author = uiState.bookDetails?.author ?: "",
                            isbn = uiState.bookDetails?.isbn ?: "",
                            publicationDate = uiState.bookDetails?.publicationDate ?: "",
                            description = uiState.bookDetails?.description ?: ""
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookDetails(
    img: String,
    title: String,
    author: String,
    isbn: String,
    publicationDate: String,
    description: String
) {
    Column(
        modifier = horizontalPaddingModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val painter = rememberVectorPainter(
            image = Icons.Default.AccountBox
        )
        GlideImage(
            model = img,
            contentDescription = stringResource(id = R.string.image_of_the_book),
            modifier = Modifier
                .size(width = 150.dp, height = 250.dp),
            contentScale = ContentScale.Crop,
            loading = placeholder(painter = painter), // FIXME: https://github.com/bumptech/glide/issues/5308
            transition = CrossFade
        )
        Text(
            text = title,
            style = Typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(
                id = R.string.author,
                author
            ),
            style = Typography.bodyMedium
        )
        if (isbn.isNotEmpty()) {
            Text(
                text = stringResource(
                    id = R.string.isbn,
                    isbn
                ),
                style = Typography.bodyMedium
            )
        }
        if (publicationDate.isNotEmpty()) {
            Text(
                text = stringResource(
                    id = R.string.publication_date,
                    publicationDate
                ),
                style = Typography.bodyMedium
            )
        }
        Divider()
        Text(
            text = description,
            style = Typography.bodySmall
        )
    }
}

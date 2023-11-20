package com.example.booksassignment.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.booksassignment.R
import com.example.booksassignment.data.models.Book
import com.example.booksassignment.data.models.BooksList
import com.example.booksassignment.ui.theme.BlueWhite
import com.example.booksassignment.ui.theme.Typography

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    style = Typography.bodyLarge
                )
                BooksLists(
                    booksLists = uiState.booksLists
                )
            }
        }
    }
}

@Composable
fun BooksLists(
    booksLists: List<BooksList>
) {
    Column(
        modifier = Modifier.verticalScroll(
            enabled = true,
            state = rememberScrollState()
        )
    ) {
        booksLists.forEach { booksList ->
            BooksList(
                title = booksList.title,
                books = booksList.books
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BooksList(
    title: String,
    books: List<Book>
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = BlueWhite
        ),
        shape = ShapeDefaults.Small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 4.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title
            )

            TextButton(
                onClick = { },
                border = BorderStroke(1.dp, BlueWhite)
            ) {
                Text(
                    text = stringResource(id = R.string.all),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = books,
                key = { item -> item.id }
            ) { book ->
                GlideImage(
                    model = book.img,
                    contentDescription = stringResource(id = R.string.image_of_the_book),
                    loading = placeholder(
                        painter = rememberVectorPainter(image = Icons.Default.Refresh)
                    ),
                    failure = placeholder(
                        painter = rememberVectorPainter(image = Icons.Default.Warning)
                    ),
                    transition = CrossFade
                )
            }
        }
    }
}

package com.example.booksassignment.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.booksassignment.R
import com.example.booksassignment.ui.theme.BooksTheme

@Composable
fun BooksEmptyView(
    @StringRes textId: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = textId)
        )
    }
}

@Composable
@Preview
private fun BooksEmptyViewPreview() {
    BooksTheme {
        BooksEmptyView(
            textId = R.string.no_books_found
        )
    }
}
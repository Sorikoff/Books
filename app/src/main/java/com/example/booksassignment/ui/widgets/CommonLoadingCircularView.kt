package com.example.booksassignment.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.booksassignment.ui.theme.BooksTheme

@Composable
fun CommonLoadingCircularView() {
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
}

@Composable
@Preview
private fun CommonLoadingCircularViewPreview() {
    BooksTheme {
        CommonLoadingCircularView()
    }
}

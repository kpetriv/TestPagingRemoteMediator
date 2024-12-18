package com.example.pagedusers.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColumnCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    elevation: Dp = 3.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.widthIn(max = 550.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(cornerRadius)),
        elevation = elevation
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                horizontal = 16.dp, vertical = 12.dp
            )
        ) {
            content()
        }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(24.dp),
            color = MaterialTheme.colors.primary
        )
    }
}


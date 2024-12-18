package com.example.pagedusers

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.pagedusers.compose.ColumnCard
import com.example.pagedusers.compose.Loading
import com.example.pagedusers.ui.theme.PagedUsersTheme
import com.example.pagedusers.ui.viewmodel.PagedUserViewModel
import com.example.pagedusers.ui.viewmodel.UserUi
import com.example.pagedusers.ui.viewmodel.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagedUsersTheme {

                val viewModel = getViewModel<PagedUserViewModel>(
                    factoryProducer = { providePagedUserViewModel() }
                )
                val usersData = viewModel.flow.collectAsLazyPagingItems()
                Content(
                    userData = usersData,
                    onError = { showErrorToast(this@MainActivity) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun Content(
    userData: LazyPagingItems<UserUi>,
    modifier: Modifier = Modifier,
    onError: () -> Unit,
) {
    Surface(
        modifier = modifier, color = MaterialTheme.colors.background
    ) {
        Box(
            modifier = modifier,
        ) {
            val listState = rememberLazyListState()
            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                modifier = modifier,
            ) {
                items(userData) { user ->
                    ColumnCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = user?.name ?: "")
                        Text(text = user?.email ?: "")
                    }
                }

                with(userData) {
                    when {
                        loadState.refresh is
                                LoadState.Loading -> {
                            item { Loading() }
                        }
                        loadState.append is
                                LoadState.Loading -> {
                            item { Loading() }
                        }
                        loadState.refresh is
                                LoadState.Error -> {
                            onError()
                        }
                        loadState.append is
                                LoadState.Error -> {
                            onError()
                        }
                    }
                }
            }
        }
    }
}

private fun showErrorToast(context: Context) {
    Toast.makeText(context, "There has been an error loading data", Toast.LENGTH_SHORT).show()
}
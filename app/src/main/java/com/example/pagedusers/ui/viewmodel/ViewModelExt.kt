package com.example.pagedusers.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pagedusers.App
import com.example.pagedusers.di.ViewModelFactory


@Composable
inline fun <reified VM : ViewModel> getViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    noinline factoryProducer: ViewModelFactory.() -> ViewModelProvider.Factory,
): VM = viewModel(
    modelClass = VM::class.java,
    factory = App.instance.injection.viewModelFactory.factoryProducer(),
    key = key,
    viewModelStoreOwner = viewModelStoreOwner,
)

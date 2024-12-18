package com.example.pagedusers.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagedusers.common.repositories.UserRepository
import com.example.pagedusers.common.usecases.GetPagedUsersUseCase
import com.example.pagedusers.ui.viewmodel.PagedUserViewModel

class ViewModelFactory(
    private val useCaseFactory: Injection.UseCaseFactory
) {

    @Suppress("UNCHECKED_CAST")
    private fun <V : ViewModel> viewModelFactory(provider: () -> V) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                provider() as T
        }

    fun providePagedUserViewModel() = viewModelFactory {
        PagedUserViewModel(useCaseFactory.getPagedUsers)
    }
}
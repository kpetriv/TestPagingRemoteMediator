package com.example.pagedusers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.map
import com.example.pagedusers.common.usecases.GetPagedUsersUseCase
import kotlinx.coroutines.flow.map

class PagedUserViewModel(
    getPagedUsersUseCase: GetPagedUsersUseCase,
) : ViewModel() {

    val flow = getPagedUsersUseCase().map {
        it.map { user ->
            UserUi(
                name = "${user.name.firstName} ${user.name.lastName}",
                email = user.email
            )
        }
    }
}

data class UserUi(
    val name: String,
    val email: String,
)
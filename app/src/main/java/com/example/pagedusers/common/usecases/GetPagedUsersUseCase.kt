package com.example.pagedusers.common.usecases

import androidx.paging.PagingData
import com.example.pagedusers.common.models.User
import kotlinx.coroutines.flow.Flow

interface GetPagedUsersUseCase {
    operator fun invoke(): Flow<PagingData<User>>
}
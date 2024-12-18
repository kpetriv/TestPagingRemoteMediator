package com.example.pagedusers.usecases

import androidx.paging.PagingData
import com.example.pagedusers.common.models.User
import com.example.pagedusers.common.repositories.UserRepository
import com.example.pagedusers.common.usecases.GetPagedUsersUseCase
import kotlinx.coroutines.flow.Flow

private const val pageSize = 50

class GetPagedUsersUseCaseImpl(
    private val userRepository: UserRepository
) : GetPagedUsersUseCase {
    override fun invoke(): Flow<PagingData<User>> =
        userRepository.getUsers(pageSize = pageSize)
}
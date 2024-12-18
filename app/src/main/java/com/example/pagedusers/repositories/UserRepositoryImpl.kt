package com.example.pagedusers.repositories

import androidx.paging.*
import com.example.pagedusers.common.models.User
import com.example.pagedusers.common.repositories.UserRepository
import com.example.pagedusers.common.transformers.toModel
import com.example.pagedusers.database.AppDatabase
import com.example.pagedusers.network.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val database: AppDatabase,
    private val service: UserService,
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(pageSize: Int): Flow<PagingData<User>> {

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = true
            ),
            remoteMediator = UserRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = { database.userDao().getUsers() }
        ).flow.map { pagingData ->
            pagingData.map { user -> user.toModel() }
        }
    }
}
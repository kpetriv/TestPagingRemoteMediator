package com.example.pagedusers.common.repositories

import androidx.paging.PagingData
import com.example.pagedusers.common.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(pageSize: Int): Flow<PagingData<User>>
}
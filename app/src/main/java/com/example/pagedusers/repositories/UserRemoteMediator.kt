package com.example.pagedusers.repositories


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pagedusers.common.transformers.toEntity
import com.example.pagedusers.database.AppDatabase
import com.example.pagedusers.database.RemoteKeys
import com.example.pagedusers.database.User
import com.example.pagedusers.network.services.UserService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userService: UserService,
    private val database: AppDatabase
) : RemoteMediator<Int, User>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
        }

        try {
            val response = userService.getUsers(page = page, size = state.config.pageSize)
            val users = response.results
            val endOfPaginationReached = users.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.userDao().clearUsers()
                    database.remoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = users.map {
                    RemoteKeys(email = it.email, prevKey = prevKey, nextKey = nextKey)
                }
                database.userDao().insertAll(*users.map { it.toEntity() }.toTypedArray())
                database.remoteKeysDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                database.remoteKeysDao().remoteKeysUserEmail(user.email)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                database.remoteKeysDao().remoteKeysUserEmail(user.email)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, User>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.email?.let { userMail ->
                database.remoteKeysDao().remoteKeysUserEmail(userMail)
            }
        }
    }
}
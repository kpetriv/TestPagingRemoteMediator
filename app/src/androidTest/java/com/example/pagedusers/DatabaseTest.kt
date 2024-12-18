package com.example.pagedusers

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.example.pagedusers.database.AppDatabase
import com.example.pagedusers.database.RemoteKeys
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import com.example.pagedusers.database.User as UserEntity

@MediumTest
class DatabaseTest {

    private lateinit var database: AppDatabase

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun closeDatabase() {
        database.clearAllTables()
        database.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDatabase() = runTest {
        val users = listOf(
            UserEntity(
                id = 1, email = "john.doe@gmail.com", firstName = "John", lastName = "Doe",
            ),
            UserEntity(
                id = 2, email = "kathy.jones@gmail.com", firstName = "Kathy", lastName = "Jones",
            )
        )

        val keys = users.map {
            RemoteKeys(email = it.email, prevKey = 0, nextKey = 1)
        }
        database.remoteKeysDao().insertAll(keys)
        database.userDao().insertAll(*users.toTypedArray())
        val page = database.userDao().getUsers().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 2, placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(
            actual = page.data,
            expected = users,
        )
    }
}
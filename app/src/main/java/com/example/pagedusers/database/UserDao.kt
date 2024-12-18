package com.example.pagedusers.database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user order by id asc")
    fun getUsers(): PagingSource<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Query("DELETE FROM user")
    suspend fun clearUsers()
}
package com.example.pagedusers.network.services

import com.example.pagedusers.network.dtos.Page
import com.example.pagedusers.network.dtos.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("/api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") size: Int,
        @Query("seed") seed: String? = "abc",
        @Query("inc") include: String? = "name,email",
    ): Page<User>
}
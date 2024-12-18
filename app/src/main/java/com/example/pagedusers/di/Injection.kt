package com.example.pagedusers.di

import android.app.Application
import androidx.compose.runtime.Stable
import com.example.pagedusers.common.repositories.UserRepository
import com.example.pagedusers.common.usecases.GetPagedUsersUseCase
import com.example.pagedusers.database.AppDatabase
import com.example.pagedusers.network.createRetrofitInstance
import com.example.pagedusers.network.createService
import com.example.pagedusers.network.services.UserService
import com.example.pagedusers.repositories.UserRepositoryImpl
import com.example.pagedusers.usecases.GetPagedUsersUseCaseImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class Injection(val application: Application) {

    // region Network
    private val okHttpClient: OkHttpClient by lazy { OkHttpClient.Builder().build() }

    private val retrofit: Retrofit by lazy { createRetrofitInstance(okHttpClient) }

    private val userService: UserService by lazy { createService(retrofit) }
    // endregion

    // region Data
    private val database: AppDatabase by lazy { AppDatabase.getInstance(application) }
    // endregion

    // region Repositories
    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            database = database,
            service = userService,
        )
    }
    // endregion

    // region VM
    val viewModelFactory by lazy { ViewModelFactory(useCaseFactory = useCaseFactory) }
    // endregion

    // region UseCase
    private val useCaseFactory by lazy { UseCaseFactory() }

    inner class UseCaseFactory {
        val getPagedUsers: GetPagedUsersUseCase
            get() = GetPagedUsersUseCaseImpl(userRepository = userRepository)
    }
    // endregion
}
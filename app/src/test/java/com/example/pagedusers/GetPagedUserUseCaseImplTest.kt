package com.example.pagedusers

import com.example.pagedusers.common.repositories.UserRepository
import com.example.pagedusers.common.usecases.GetPagedUsersUseCase
import com.example.pagedusers.usecases.GetPagedUsersUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

internal class GetPagedUserUseCaseImplTest {

    private val userRepository: UserRepository = mockk()
    private val getPagedUserUseCase: GetPagedUsersUseCase = GetPagedUsersUseCaseImpl(
        userRepository = userRepository
    )

    @Test
    fun `calls the use case once`() {
        every { userRepository.getUsers(any()) } returns flowOf(mockk())
        getPagedUserUseCase(pageSize = 20)
        verify(exactly = 1) { userRepository.getUsers(any()) }
    }
}
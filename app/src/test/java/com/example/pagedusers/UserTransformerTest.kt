package com.example.pagedusers

import com.example.pagedusers.common.transformers.toEntity
import com.example.pagedusers.common.transformers.toModel
import org.junit.Test
import kotlin.test.assertEquals
import com.example.pagedusers.common.models.User as UserModel
import com.example.pagedusers.database.User as UserEntity
import com.example.pagedusers.network.dtos.User as UserDto

internal class UserTransformerTest {

    @Test
    fun entityToModel() {
        assertEquals(
            actual = UserEntity(
                id = 1, email = "john.doe@gmail.com", firstName = "John", lastName = "Doe"
            ).toModel(),
            expected = UserModel(
                email = "john.doe@gmail.com",
                name = UserModel.Name(firstName = "John", lastName = "Doe")
            ),
        )
    }

    @Test
    fun dtoToEntity() {
        assertEquals(
            actual = UserDto(
                name = UserDto.Name(first = "John", last = "Doe"),
                email = "john.doe@gmail.com",
            ).toEntity(),
            expected = UserEntity(
                id = 0,
                email = "john.doe@gmail.com",
                firstName = "John",
                lastName = "Doe"
            ),
        )
    }
}
package com.example.pagedusers.common.transformers

import com.example.pagedusers.common.models.User as UserModel
import com.example.pagedusers.database.User as UserEntity
import com.example.pagedusers.network.dtos.User as UserDto

fun UserEntity.toModel() = UserModel(
    email = this.email,
    name = com.example.pagedusers.common.models.User.Name(
        firstName = this.firstName,
        lastName = this.lastName
    )
)

fun UserDto.toEntity() = UserEntity(
    email = this.email,
    firstName = name.first,
    lastName = name.last
)
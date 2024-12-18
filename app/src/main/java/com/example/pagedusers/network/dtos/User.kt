package com.example.pagedusers.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class User(
    @SerialName("name") val name: Name,
    @SerialName("email") val email: String,
) {
    @Serializable
    class Name(
        @SerialName("first") val first: String,
        @SerialName("last") val last: String,
    )
}
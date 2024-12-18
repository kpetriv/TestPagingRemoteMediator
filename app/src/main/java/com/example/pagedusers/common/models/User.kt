package com.example.pagedusers.common.models

data class User(
    val email: String,
    val name: Name,
) {
    data class Name(
        val firstName: String,
        val lastName: String,
    )
}
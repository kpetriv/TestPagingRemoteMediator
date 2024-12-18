package com.example.pagedusers.database

import androidx.room.*

@Entity(
    tableName = "user",
    indices = [
        Index(value = ["email"], unique = true),
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
)
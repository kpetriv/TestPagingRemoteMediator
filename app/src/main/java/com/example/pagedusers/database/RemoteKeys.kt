package com.example.pagedusers.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_keys",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["email"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["email"], unique = true),
    ]
)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val prevKey: Int?,
    val nextKey: Int?
)
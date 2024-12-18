package com.example.pagedusers.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Page<T>(
    @SerialName("results") val results: List<T>,
    @SerialName("info") val info: Info,
) {
    @Serializable
    class Info(
        @SerialName("seed") val seed: String,
        @SerialName("results") val results: Int,
        @SerialName("page") val page: Int,
        @SerialName("version") val version: String,
    )
}
package com.gzaber.forexviewer.data.repository.favorites.model

data class Favorite(
    val id: Int? = null,
    val symbol: String,
    val base: String,
    val quote: String
)

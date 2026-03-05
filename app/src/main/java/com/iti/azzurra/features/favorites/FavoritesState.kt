package com.iti.azzurra.features.favorites

data class FavoritesState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)
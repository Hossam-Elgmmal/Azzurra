package com.iti.azzurra.features.home

sealed interface HomeAction {
    data object FetchNewData: HomeAction
}
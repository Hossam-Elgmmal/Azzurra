package com.iti.azzurra.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FavoritesState()
        )

    fun onAction(action: FavoritesAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}
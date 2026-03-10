package com.iti.azzurra

import androidx.lifecycle.ViewModel
import com.iti.azzurra.data.settings.UserSettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userSettingsRepo: UserSettingsRepo
) : ViewModel() {

    val mainUiState: Flow<MainUiState> = userSettingsRepo.getUserSettingsFlow()
        .map {
            MainUiState.Ready(it)
        }

}
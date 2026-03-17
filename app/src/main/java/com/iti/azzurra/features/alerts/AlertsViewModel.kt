package com.iti.azzurra.features.alerts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarAction
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.data.alert.AlertRepo
import com.iti.azzurra.data.alert.local.models.AlertEntity
import com.iti.azzurra.utils.Constants.ERROR_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val alertRepo: AlertRepo
) : ViewModel() {

    private val _state = MutableStateFlow(AlertsState())
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = alertRepo.getAllAlerts()
        .flatMapLatest { alerts ->
            Log.e(ERROR_TAG, "flatMapLatest: $alerts", )
            _state.map {
                it.copy(allAlerts = alerts)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AlertsState()
        )

    fun onAction(action: AlertsAction) {
        when (action) {
            is AlertsAction.AddAlert -> {
                saveAlert(action.alert)
            }
            is AlertsAction.OpenAddAlertDialog -> {
                _state.update {
                    it.copy(
                        showAddAlertDialog = action.open
                    )
                }
            }
            is AlertsAction.ShowNotificationDialog -> {
                _state.update {
                    it.copy(
                        showNotificationPermissionDialog = action.open
                    )
                }
            }
            is AlertsAction.ToggleActivateAlert -> {
                val newAlert = action.alert.copy(isActive = action.newValue)
                saveAlert(newAlert)
            }
            is AlertsAction.DeleteAlert -> {
                deleteAlert(action)
            }
        }
    }

    private fun deleteAlert(action: AlertsAction.DeleteAlert) {
        viewModelScope.launch {
            alertRepo.deleteAlertById(action.alert.alertId)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = R.string.alert_deleted,
                    snackbarAction = SnackbarAction(
                        R.string.undo,
                        action = {
                            saveAlert(action.alert)
                        }
                    )
                )
            )
        }
    }

    private fun saveAlert(alert: AlertEntity) {
        viewModelScope.launch {
            alertRepo.insertAlert(alert)
        }
    }

}
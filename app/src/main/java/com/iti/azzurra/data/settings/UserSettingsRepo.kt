package com.iti.azzurra.data.settings

import com.iti.azzurra.data.settings.models.UserSettings
import kotlinx.coroutines.flow.StateFlow

interface UserSettingsRepo {
    val settingsFlow: StateFlow<UserSettings>
    suspend fun updateUserSettings(updateBlock: (UserSettings) -> UserSettings)
}
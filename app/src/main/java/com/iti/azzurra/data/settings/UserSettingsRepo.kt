package com.iti.azzurra.data.settings

import com.iti.azzurra.data.settings.models.UserSettings
import kotlinx.coroutines.flow.SharedFlow

interface UserSettingsRepo {
    val settingsFlow: SharedFlow<UserSettings>
    suspend fun updateUserSettings(updateBlock: (UserSettings) -> UserSettings)
}
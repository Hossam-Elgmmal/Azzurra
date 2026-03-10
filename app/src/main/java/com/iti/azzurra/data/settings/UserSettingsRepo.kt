package com.iti.azzurra.data.settings

import com.iti.azzurra.data.settings.models.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepo {
    fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun updateUserSettings(updateBlock: (UserSettings) -> UserSettings)
}
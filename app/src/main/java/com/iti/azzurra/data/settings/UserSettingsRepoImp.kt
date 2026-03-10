package com.iti.azzurra.data.settings

import android.util.Log
import androidx.datastore.core.DataStore
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.utils.Constants.ERROR_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.io.IOException
import javax.inject.Inject

class UserSettingsRepoImp @Inject constructor(
    private val dataStore: DataStore<UserSettings>
) : UserSettingsRepo {

    override fun getUserSettingsFlow(): Flow<UserSettings> {
        return dataStore.data
    }

    override suspend fun updateUserSettings(updateBlock: (UserSettings) -> UserSettings) {
        try {
            dataStore.updateData { currentData ->
                updateBlock(currentData)
            }
        } catch (e: IOException) {
            Log.e(ERROR_TAG, "Failed to update user settings: ${e.localizedMessage}", e)
        }
    }
}
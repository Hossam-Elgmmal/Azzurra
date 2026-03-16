package com.iti.azzurra.data.settings

import android.util.Log
import androidx.datastore.core.DataStore
import com.iti.azzurra.core.scope.ApplicationScope
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.utils.Constants.ERROR_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.io.IOException
import javax.inject.Inject

class UserSettingsRepoImp @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
    @param:ApplicationScope private val scope: CoroutineScope
) : UserSettingsRepo {

    private val _settingsFlow: Flow<UserSettings> = dataStore.data
    override val settingsFlow: StateFlow<UserSettings> = _settingsFlow.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserSettings()
    )

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
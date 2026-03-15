package com.iti.azzurra.data.settings

import android.util.Log
import androidx.datastore.core.DataStore
import com.iti.azzurra.core.scope.ApplicationScope
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.utils.Constants.ERROR_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import java.io.IOException
import javax.inject.Inject

class UserSettingsRepoImp @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
    @param:ApplicationScope private val scope: CoroutineScope
) : UserSettingsRepo {

    private val _settingsFlow: Flow<UserSettings> = dataStore.data
    override val settingsFlow: SharedFlow<UserSettings> = _settingsFlow.shareIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000),
        replay = 1
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
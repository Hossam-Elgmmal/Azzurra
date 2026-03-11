package com.iti.azzurra.data.settings.models

import com.iti.azzurra.R
import kotlinx.serialization.Serializable

@Serializable
enum class ThemeSetting {
    LIGHT,
    DARK,
    FOLLOW_SYSTEM;

    fun getTitleId(): Int {
        return when (this) {
            LIGHT -> R.string.light
            DARK -> R.string.dark
            FOLLOW_SYSTEM -> R.string.follow_system
        }
    }
}

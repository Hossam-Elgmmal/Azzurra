package com.iti.azzurra.data.settings.models

import kotlinx.serialization.Serializable

@Serializable
enum class LanguageSetting {
    ENGLISH,
    ARABIC;
    fun getCode(): String {
        return when (this) {
            ENGLISH -> "en"
            ARABIC -> "ar"
        }
    }
}
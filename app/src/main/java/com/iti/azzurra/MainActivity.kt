package com.iti.azzurra

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.iti.azzurra.common.ExceptionLogger
import com.iti.azzurra.data.settings.models.LanguageSetting
import com.iti.azzurra.features.home.HomeRoute
import com.iti.azzurra.main_navigation.MainNavigation
import com.iti.azzurra.ui.theme.AzzurraTheme
import com.iti.azzurra.utils.Constants.ERROR_TAG
import com.iti.azzurra.utils.Constants.EXCEPTION_REQUEST_CODE
import com.iti.azzurra.utils.getTypography
import com.iti.azzurra.utils.shouldShowDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            mainUiState == MainUiState.Loading
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState.collect {
                    mainUiState = it
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ExceptionLogger.events.collect {
                    handleExceptions(it)
                }
            }
        }

        lifecycleScope.launch {
            val locales = AppCompatDelegate.getApplicationLocales()
            val langTag = locales.toLanguageTags()
            val languageSetting = when (langTag) {
                "ar" -> LanguageSetting.ARABIC
                else -> LanguageSetting.ENGLISH
            }
            viewModel.saveLanguageSettings(languageSetting)
        }

        enableEdgeToEdge()
        setContent {
            val isDarkTheme = shouldShowDarkTheme(mainUiState)
            AzzurraTheme(
                darkTheme = isDarkTheme,
                typography = getTypography(mainUiState)
            ) {
                MainNavigation(
                    startDestination = HomeRoute,
                    mainUiState = mainUiState,
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }

    private fun handleExceptions(exception: Throwable) {
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(
                    this,
                    EXCEPTION_REQUEST_CODE
                )
            } catch (sendEx: IntentSender.SendIntentException) {
                Log.e(ERROR_TAG, "handleExceptions: ${sendEx.localizedMessage}", sendEx)
            }
        } else {
            Log.e(
                ERROR_TAG,
                "handleExceptions: ${exception.localizedMessage}",
                exception
            )
        }
    }
}
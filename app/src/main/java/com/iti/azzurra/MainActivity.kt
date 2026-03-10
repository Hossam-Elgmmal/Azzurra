package com.iti.azzurra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.iti.azzurra.features.home.HomeRoute
import com.iti.azzurra.main_navigation.MainNavigation
import com.iti.azzurra.ui.theme.AzzurraTheme
import com.iti.azzurra.utils.getTypography
import com.iti.azzurra.utils.shouldShowDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
}
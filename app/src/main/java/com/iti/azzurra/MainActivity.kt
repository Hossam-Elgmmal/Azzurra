package com.iti.azzurra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iti.azzurra.features.home.HomeRoute
import com.iti.azzurra.main_navigation.MainNavigation
import com.iti.azzurra.ui.theme.AzzurraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AzzurraTheme {
                MainNavigation(
                    startDestination = HomeRoute
                )
            }
        }
    }
}
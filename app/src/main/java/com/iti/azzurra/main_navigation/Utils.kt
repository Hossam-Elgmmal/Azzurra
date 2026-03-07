package com.iti.azzurra.main_navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.cloudy.Sky
import com.skydoves.cloudy.rememberSky


val LocalBottomBarHeight = staticCompositionLocalOf { 0.dp }
val LocalSky: ProvidableCompositionLocal<Sky> = staticCompositionLocalOf { error("No sky available") }
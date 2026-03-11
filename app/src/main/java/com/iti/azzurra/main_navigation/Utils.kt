package com.iti.azzurra.main_navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState


val LocalBottomBarHeight = staticCompositionLocalOf { 0.dp }
val LocalHazeState: ProvidableCompositionLocal<HazeState> = staticCompositionLocalOf { HazeState() }
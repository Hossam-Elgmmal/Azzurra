package com.iti.azzurra.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.iti.azzurra.R

private val fontFamily = FontFamily(
    Font(
        resId = R.font.manrope_light,
        weight = FontWeight.Light,
    ),
    Font(
        resId = R.font.manrope,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.manrope_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.manrope_semibold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.manrope_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.manrope_extrabold,
        weight = FontWeight.ExtraBold,
    ),
)

private val baseline = Typography()

val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = fontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = fontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = fontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = fontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = fontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = fontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = fontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = fontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = fontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = fontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = fontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = fontFamily),
)
package com.iti.azzurra.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.iti.azzurra.R

private val ManropeFontFamily = FontFamily(
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
private val AmiriFontFamily = FontFamily(
    Font(
        resId = R.font.amiri,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.amiri,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.amiri_bold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.amiri_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.amiri_bold,
        weight = FontWeight.ExtraBold,
    ),
)
private val AlmaraiFontFamily = FontFamily(
    Font(
        resId = R.font.almarai,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.almarai,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.almarai_bold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.almarai_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.almarai_bold,
        weight = FontWeight.ExtraBold,
    ),
)

private val baseline = Typography()

val EnglishTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = AmiriFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = AmiriFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = AmiriFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = AmiriFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = AmiriFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = AmiriFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = ManropeFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = ManropeFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = ManropeFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = ManropeFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = ManropeFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = ManropeFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = ManropeFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = ManropeFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = ManropeFontFamily),
)

val ArabicTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = AmiriFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = AmiriFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = AmiriFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = AmiriFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = AmiriFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = AmiriFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = AlmaraiFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = AlmaraiFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = AlmaraiFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = AlmaraiFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = AlmaraiFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = AlmaraiFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = AlmaraiFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = AlmaraiFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = AlmaraiFontFamily),
)
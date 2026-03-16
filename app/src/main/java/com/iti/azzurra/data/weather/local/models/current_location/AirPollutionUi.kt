package com.iti.azzurra.data.weather.local.models.current_location

data class AirPollutionUi(
    val locationId: String,
    val airQualityIndex: Int,
    val airQualityLevelText: String,// e.g. "Good", "Fair", "Moderate", "Poor", "Very Poor"
    val carbonMonoxideText: String,
    val nitrogenMonoxideText: String,
    val nitrogenDioxideText: String,
    val ozoneText: String,
    val sulphurDioxideText: String,
    val particulateMatter2AndHalfText: String,
    val particulateMatter10Text: String,
    val ammoniaText: String,
)
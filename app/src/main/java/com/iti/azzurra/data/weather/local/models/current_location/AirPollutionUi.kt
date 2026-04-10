package com.iti.azzurra.data.weather.local.models.current_location

data class AirPollutionUi(
    val locationId: String = "_",
    val airQualityIndex: Int = 1,
    val airQualityLevelText: String = "_",// e.g. "Good", "Fair", "Moderate", "Poor", "Very Poor"
    val carbonMonoxideText: String = "_",
    val nitrogenMonoxideText: String = "_",
    val nitrogenDioxideText: String = "_",
    val ozoneText: String = "_",
    val sulphurDioxideText: String = "_",
    val particulateMatter2AndHalfText: String = "_",
    val particulateMatter10Text: String = "_",
    val ammoniaText: String = "_",
)
package com.iti.azzurra.data.weather.mappers

import android.content.Context
import com.iti.azzurra.R
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionUi
import com.iti.azzurra.data.weather.remote.models.pollution.AirPollutionForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.pollution.AirPollutionItemDto

fun AirPollutionForecastResponseDto.toAirPollutionEntities(
    locationId: String
): List<AirPollutionEntity> {
    return pollutionItems
        .orEmpty()
        .map {
            it.toAirPollutionEntity(locationId)
        }
}

private fun AirPollutionItemDto.toAirPollutionEntity(locationId: String) = AirPollutionEntity(
    locationId = locationId,
    timestamp = timestamp ?: 0L,
    airQualityIndex = index?.airQualityIndex ?: 0,
    carbonMonoxide = components?.carbonMonoxide ?: 0.0,
    nitrogenMonoxide = components?.nitrogenMonoxide ?: 0.0,
    nitrogenDioxide = components?.nitrogenDioxide ?: 0.0,
    ozone = components?.ozone ?: 0.0,
    sulphurDioxide = components?.sulphurDioxide ?: 0.0,
    particulateMatter2AndHalf = components?.particulateMatter2AndHalf ?: 0.0,
    particulateMatter10 = components?.particulateMatter10 ?: 0.0,
    ammonia = components?.ammonia ?: 0.0
)

fun List<AirPollutionEntity>.toAirPollutionUiList(
    context: Context,
): List<AirPollutionUi> {
    return map {
        it.toAirPollutionUi(context)
    }
}

fun AirPollutionEntity.toAirPollutionUi(
    context: Context,
): AirPollutionUi {

    return AirPollutionUi(
        locationId = locationId,
        airQualityIndex = airQualityIndex,
        airQualityLevelText = context.getString(resolveAirQualityLevelRes(airQualityIndex)),
        carbonMonoxideText = context.getString(
            R.string.unit_micrograms_per_cubic_meter,
            carbonMonoxide.toString()
        ),
        nitrogenMonoxideText = context.getString(
            R.string.unit_micrograms_per_cubic_meter,
            nitrogenMonoxide.toString()
        ),
        nitrogenDioxideText = context.getString(
            R.string.unit_micrograms_per_cubic_meter,
            nitrogenDioxide.toString()
        ),
        ozoneText = context.getString(R.string.unit_micrograms_per_cubic_meter, ozone),
        sulphurDioxideText = context.getString(
            R.string.unit_micrograms_per_cubic_meter,
            sulphurDioxide.toString()
        ),
        particulateMatter2AndHalfText = context.getString(
            R.string.unit_micrograms_per_cubic_meter,
            particulateMatter2AndHalf.toString()
        ),
        particulateMatter10Text = context.getString(
            R.string.unit_micrograms_per_cubic_meter,
            particulateMatter10.toString()
        ),
        ammoniaText = context.getString(R.string.unit_micrograms_per_cubic_meter, ammonia),
    )
}


private fun resolveAirQualityLevelRes(airQualityIndex: Int): Int = when (airQualityIndex) {
    1 -> R.string.aqi_level_good
    2 -> R.string.aqi_level_fair
    3 -> R.string.aqi_level_moderate
    4 -> R.string.aqi_level_poor
    else -> R.string.aqi_level_very_poor
}
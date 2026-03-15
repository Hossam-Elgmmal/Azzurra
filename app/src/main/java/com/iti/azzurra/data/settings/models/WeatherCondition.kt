package com.iti.azzurra.data.settings.models

import com.iti.azzurra.R
import kotlinx.serialization.Serializable

@Serializable
enum class WeatherCondition {
    THUNDERSTORM,
    DRIZZLE,
    RAIN,
    HEAVY_RAIN,
    FREEZING_RAIN,
    SNOW,
    HEAVY_SNOW,
    MIST,
    SMOKE,
    HAZE,
    DUST,
    FOG,
    ASH,
    SQUALL,
    TORNADO,
    CLEAR,
    FEW_CLOUDS,
    CLOUDY,
    UNKNOWN;

    companion object {
        fun getWeatherCondition(id: Int): WeatherCondition {
            return when (id) {
                781 -> TORNADO
                771 -> SQUALL
                762 -> ASH
                511 -> FREEZING_RAIN
                in 502..504 -> HEAVY_RAIN
                602 -> HEAVY_SNOW
                in 200..232 -> THUNDERSTORM
                in 300..321 -> DRIZZLE
                in 500..531 -> RAIN
                in 600..622 -> SNOW
                711 -> SMOKE
                741 -> FOG
                in 731..761 -> DUST
                721 -> HAZE
                701 -> MIST
                in 803..804 -> CLOUDY
                in 801..802 -> FEW_CLOUDS
                800 -> CLEAR
                else -> UNKNOWN
            }
        }
    }

    fun getImageId(): Int {
        return when (this) {
            THUNDERSTORM -> R.drawable.img_thunder_storm
            DRIZZLE -> R.drawable.img_drizzle
            RAIN -> R.drawable.img_rain
            HEAVY_RAIN -> R.drawable.img_heavy_rain
            FREEZING_RAIN -> R.drawable.img_freezing_rain
            SNOW -> R.drawable.img_snow
            HEAVY_SNOW -> R.drawable.img_heavy_snow
            MIST -> R.drawable.img_mist
            SMOKE -> R.drawable.img_smoke
            HAZE -> R.drawable.img_haze
            DUST -> R.drawable.img_dust
            FOG -> R.drawable.img_fog
            ASH -> R.drawable.img_ash
            SQUALL -> R.drawable.img_squall
            TORNADO -> R.drawable.img_tornado
            CLEAR -> R.drawable.img_clear
            FEW_CLOUDS -> R.drawable.img_few_clouds
            CLOUDY -> R.drawable.img_cloudy
            UNKNOWN -> R.drawable.img_unknown
        }
    }
}
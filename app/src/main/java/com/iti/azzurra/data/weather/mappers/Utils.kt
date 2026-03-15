package com.iti.azzurra.data.weather.mappers

import android.content.Context
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.TemperatureUnit
import com.iti.azzurra.data.settings.models.WindSpeedUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.time.toJavaInstant

fun makeLocationId(
    latitude: Double,
    longitude: Double
): String {
    return "${(latitude * 100).roundToInt()}_${(longitude * 100).roundToInt()}"
}

fun Long.toStartOfDayTimestamp(): Long {
    return Instant.ofEpochSecond(this)
        .atOffset(ZoneOffset.UTC)
        .toLocalDate()
        .atStartOfDay()
        .toEpochSecond(ZoneOffset.UTC)
}


fun resolveTimeZone(utcOffsetSeconds: Int): TimeZone {
    val totalMinutes = utcOffsetSeconds / 60
    val hours = totalMinutes / 60
    val minutes = abs(totalMinutes % 60)
    val sign = if (hours >= 0) "+" else "-"
    val id = if (minutes == 0) {
        "UTC$sign${abs(hours)}"
    } else {
        "UTC$sign${abs(hours)}:${minutes.toString().padStart(2, '0')}"
    }
    return TimeZone.of(id)
}

fun formatDate(
    epochSeconds: Long,
    timeZone: TimeZone,
    locale: Locale = Locale.getDefault()
): String {
    val instant = kotlin.time.Instant.fromEpochSeconds(epochSeconds)
    val zonedDateTime = instant
        .toJavaInstant()
        .atZone(java.time.ZoneId.of(timeZone.id))
    val formatter = DateTimeFormatter.ofPattern(
        "EEEE, d MMM",
        locale
    )
    return zonedDateTime.format(formatter)
}

fun formatTime(epochSeconds: Long, timeZone: TimeZone): String {
    val local = kotlin.time.Instant.fromEpochSeconds(epochSeconds).toLocalDateTime(timeZone)
    val hour24 = local.hour
    val minute = local.minute.toString().padStart(2, '0')
    val amPm = if (hour24 < 12) "AM" else "PM"
    val hour12 = when {
        hour24 == 0  -> 12
        hour24 > 12  -> hour24 - 12
        else         -> hour24
    }
    return "$hour12:$minute $amPm"
}

fun formatTemperature(
    celsius: Double,
    context: Context,
    unit: TemperatureUnit,
): String {
    val converted = when (unit) {
        TemperatureUnit.CELSIUS    -> celsius
        TemperatureUnit.FAHRENHEIT -> celsius * 9.0 / 5.0 + 32.0
        TemperatureUnit.KELVIN     -> celsius + 273.15
    }
    val unitLabel = context.getString(
        when (unit) {
            TemperatureUnit.CELSIUS    -> R.string.unit_celsius
            TemperatureUnit.FAHRENHEIT -> R.string.unit_fahrenheit
            TemperatureUnit.KELVIN     -> R.string.unit_kelvin
        }
    )
    return "${converted.roundToInt()}$unitLabel"
}

fun formatWindSpeed(
    metersPerSec: Double,
    context: Context,
    unit: WindSpeedUnit,
): String {
    val converted = when (unit) {
        WindSpeedUnit.METER_PER_SEC      -> metersPerSec
        WindSpeedUnit.KILOMETER_PER_HOUR -> metersPerSec * 3.6
        WindSpeedUnit.MILES_PER_HOUR     -> metersPerSec * 2.23694
    }
    val unitLabel = context.getString(
        when (unit) {
            WindSpeedUnit.METER_PER_SEC      -> R.string.unit_m_per_s
            WindSpeedUnit.KILOMETER_PER_HOUR -> R.string.unit_km_per_h
            WindSpeedUnit.MILES_PER_HOUR     -> R.string.unit_mph
        }
    )
    return "%.1f %s".format(converted, unitLabel)
}

fun formatWindDirection(degrees: Int, context: Context): String {
    val directions = context.resources.getStringArray(R.array.wind_directions)

    val index = ((degrees + 11.25) / 22.5).toInt() % 16
    val compass = directions.getOrElse(index) { "?" }
    return context.getString(R.string.wind_direction_format, compass, degrees)
}

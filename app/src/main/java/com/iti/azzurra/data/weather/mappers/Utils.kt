package com.iti.azzurra.data.weather.mappers

import java.time.Instant
import java.time.ZoneOffset
import kotlin.math.roundToInt

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

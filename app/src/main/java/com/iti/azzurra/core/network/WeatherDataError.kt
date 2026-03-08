package com.iti.azzurra.core.network

import android.content.Context
import com.iti.azzurra.R

enum class WeatherDataError : WeatherError {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER,
    SERIALIZATION,
    INVALID_API_KEY,
    API_QUOTA_EXCEEDED,
    LOCATION_NOT_FOUND,
    EMPTY_RESPONSE,
    UNKNOWN;
}

fun WeatherDataError.toUserMessage(context: Context): String = when (this) {
    WeatherDataError.REQUEST_TIMEOUT -> context.getString(R.string.request_timed_out)
    WeatherDataError.TOO_MANY_REQUESTS -> context.getString(R.string.too_many_requests)
    WeatherDataError.NO_INTERNET -> context.getString(R.string.no_internet_connection)
    WeatherDataError.SERVER -> context.getString(R.string.server_error)
    WeatherDataError.SERIALIZATION -> context.getString(R.string.failed_to_process_response)
    WeatherDataError.INVALID_API_KEY -> context.getString(R.string.invalid_api_key_please_contact_support)
    WeatherDataError.API_QUOTA_EXCEEDED -> context.getString(R.string.api_limit_reached_please_try_again_in_a_few_minutes)
    WeatherDataError.LOCATION_NOT_FOUND -> context.getString(R.string.location_not_found_try_searching_for_a_different_city)
    WeatherDataError.EMPTY_RESPONSE -> context.getString(R.string.no_weather_data_received_please_try_again)
    WeatherDataError.UNKNOWN -> context.getString(R.string.something_went_wrong)
}
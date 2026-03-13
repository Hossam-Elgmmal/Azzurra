package com.iti.azzurra.core.network

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

fun WeatherDataError.toUserMessageId(): Int = when (this) {
    WeatherDataError.REQUEST_TIMEOUT -> R.string.request_timed_out
    WeatherDataError.TOO_MANY_REQUESTS -> R.string.too_many_requests
    WeatherDataError.NO_INTERNET -> R.string.no_internet_connection
    WeatherDataError.SERVER -> R.string.server_error
    WeatherDataError.SERIALIZATION -> R.string.failed_to_process_response
    WeatherDataError.INVALID_API_KEY -> R.string.invalid_api_key_please_contact_support
    WeatherDataError.API_QUOTA_EXCEEDED -> R.string.api_limit_reached_please_try_again_in_a_few_minutes
    WeatherDataError.LOCATION_NOT_FOUND -> R.string.location_not_found_try_searching_for_a_different_city
    WeatherDataError.EMPTY_RESPONSE -> R.string.no_weather_data_received_please_try_again
    WeatherDataError.UNKNOWN -> R.string.something_went_wrong
}
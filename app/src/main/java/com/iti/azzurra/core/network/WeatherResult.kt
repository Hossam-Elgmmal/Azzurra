package com.iti.azzurra.core.network


sealed interface WeatherResult<out D, out E : WeatherError> {
    data class Success<out D>(val data: D) : WeatherResult<D, Nothing>
    data class Failure<out E : WeatherError>(val error: E) : WeatherResult<Nothing, E>
}

inline fun <T, E : WeatherError, R> WeatherResult<T, E>.map(map: (T) -> R): WeatherResult<R, E> {
    return when (this) {
        is WeatherResult.Failure -> WeatherResult.Failure(error)
        is WeatherResult.Success -> WeatherResult.Success(map(data))
    }
}

inline fun <T, E : WeatherError> WeatherResult<T, E>.onSuccess(action: (T) -> Unit): WeatherResult<T, E> {
    return when (this) {
        is WeatherResult.Failure -> this
        is WeatherResult.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : WeatherError> WeatherResult<T, E>.onFailure(action: (E) -> Unit): WeatherResult<T, E> {
    return when (this) {
        is WeatherResult.Failure -> {
            action(error)
            this
        }

        is WeatherResult.Success -> this
    }
}
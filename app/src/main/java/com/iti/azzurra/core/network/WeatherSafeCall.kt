package com.iti.azzurra.core.network

import android.util.Log
import com.iti.azzurra.utils.Constants.ERROR_TAG
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    execute: suspend () -> Response<T>
): WeatherResult<T, WeatherDataError> {
    val response: Response<T> = try {
        execute()
    } catch (e: SocketTimeoutException) {
        Log.e(ERROR_TAG, "safeApiCall: ${e.localizedMessage}", e)
        return WeatherResult.Failure(WeatherDataError.REQUEST_TIMEOUT)
    } catch (e: UnknownHostException) {
        Log.e(ERROR_TAG, "safeApiCall: ${e.localizedMessage}", e)
        return WeatherResult.Failure(WeatherDataError.NO_INTERNET)
    } catch (e: IOException) {
        Log.e(ERROR_TAG, "safeApiCall: ${e.localizedMessage}", e)
        return WeatherResult.Failure(WeatherDataError.NO_INTERNET)
    } catch (e: SerializationException) {
        Log.e(ERROR_TAG, "safeApiCall: ${e.localizedMessage}", e)
        return WeatherResult.Failure(WeatherDataError.SERIALIZATION)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        Log.e(ERROR_TAG, "safeApiCall: ${e.localizedMessage}", e)
        return WeatherResult.Failure(WeatherDataError.UNKNOWN)
    }

    return responseToResult(response)
}

fun <T> responseToResult(
    response: Response<T>
): WeatherResult<T, WeatherDataError> {
    return when (response.code()) {
        in 200..299 -> {
            response.body()?.let {
                WeatherResult.Success(it)
            } ?: WeatherResult.Failure(WeatherDataError.EMPTY_RESPONSE)
        }

        401 -> WeatherResult.Failure(WeatherDataError.INVALID_API_KEY)
        404 -> WeatherResult.Failure(WeatherDataError.LOCATION_NOT_FOUND)
        408 -> WeatherResult.Failure(WeatherDataError.REQUEST_TIMEOUT)
        429 -> WeatherResult.Failure(WeatherDataError.API_QUOTA_EXCEEDED)
        in 500..599 -> WeatherResult.Failure(WeatherDataError.SERVER)
        else -> WeatherResult.Failure(WeatherDataError.UNKNOWN)
    }
}
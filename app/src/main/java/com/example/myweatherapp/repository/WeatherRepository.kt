package com.example.myweatherapp.repository

import android.util.Log
import com.example.myweatherapp.api.OpenMeteoApi
import com.example.myweatherapp.api.RetrofitClient
import com.example.myweatherapp.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {
    private val api: OpenMeteoApi = RetrofitClient.openMeteoApi

    suspend fun getWeatherForLocation(latitude: Double, longitude: Double): WeatherResponse {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("WeatherRepository", "Requesting weather data for lat: $latitude, lon: $longitude")
                val response = api.getWeather(latitude, longitude)
                Log.d("WeatherRepository", "Received weather data: $response")
                response
            } catch (e: Exception) {
                Log.e("WeatherRepository", "Error fetching weather data: ${e.message}")
                throw e
            }
        }
    }
}
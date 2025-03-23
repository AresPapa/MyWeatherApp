package com.example.myweatherapp.data

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current_weather: CurrentWeather,
    val hourly: HourlyWeather,
    val daily: DailyWeather
)

data class CurrentWeather(
    val temperature: Double,
    val weather_code: Int
)

data class HourlyWeather(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val relative_humidity_2m: List<Double>,
    val weather_code: List<Int>
)

data class DailyWeather(
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val weather_code: List<Int>
)

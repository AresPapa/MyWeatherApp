package com.example.myweatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.components.DailyWeatherDisplay
import com.example.myweatherapp.components.HourlyWeatherDisplay
import com.example.myweatherapp.components.WeatherOverview
import com.example.myweatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState()
    val hourlyWeather = viewModel.hourlyWeather.collectAsState()
    val dailyWeather = viewModel.dailyWeather.collectAsState()

    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator()
            }
            errorMessage.value != null -> {
                Text(text = "Error: ${errorMessage.value}")
            }
            currentWeather.value != null -> {
                Text(text = "Temperature: ${currentWeather.value?.temperature}°C")
                WeatherOverview( currentWeather = currentWeather.value!!, dailyWeather = dailyWeather.value!!)
                hourlyWeather.value?.let {
                    HourlyWeatherDisplay(hourlyWeather = it)
                }
                dailyWeather.value?.let {
                    DailyWeatherDisplay(dailyWeather = it)
                }
            }
            else -> {
                Text(text = "No weather data available")
            }
        }
    }
}
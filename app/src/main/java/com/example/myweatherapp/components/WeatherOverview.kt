package com.example.myweatherapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import com.example.myweatherapp.data.CurrentWeather
import com.example.myweatherapp.data.DailyWeather

@Composable
fun WeatherOverview(currentWeather: CurrentWeather, dailyWeather: DailyWeather) {
    Column (
        Modifier.padding(16.dp)
    ) {
        Text(text = "Weather Overview", fontSize = 32.sp)
        Text(text = "${currentWeather.temperature}°C", fontSize = 24.sp)
        Row {
            Text(text = "Max: ${dailyWeather.temperature_2m_max[0]}°C", fontSize = 16.sp)
            Text(text = "Min: ${dailyWeather.temperature_2m_min[0]}°C", fontSize = 16.sp)
        }
    }
}
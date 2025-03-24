package com.example.myweatherapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.data.HourlyWeather
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherDisplay(hourlyWeather: HourlyWeather) {
    val currentHour = LocalDateTime.now().hour
    val startIndex = currentHour % hourlyWeather.temperature_2m.size
    val next24Hours = hourlyWeather.time.drop(startIndex).take(24)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(hourlyWeather.temperature_2m.take(24).size) { index ->
            val time = LocalDateTime.parse(next24Hours[index]).format(formatter)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = time, style = MaterialTheme.typography.bodySmall)
                Text(text = "${hourlyWeather.temperature_2m[startIndex + index].roundToInt()}°C", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Humidity: ${hourlyWeather.relative_humidity_2m[startIndex + index].roundToInt()}%", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

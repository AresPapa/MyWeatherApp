package com.example.myweatherapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.data.DailyWeather
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DailyWeatherDisplay(dailyWeather: DailyWeather) {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d")

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(dailyWeather.temperature_2m_max.size) { index ->
            val date = LocalDate.now().plusDays(index.toLong()).format(formatter)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = date, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                    Text(text = "Max: ${dailyWeather.temperature_2m_max[index]}°C", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Min: ${dailyWeather.temperature_2m_min[index]}°C", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

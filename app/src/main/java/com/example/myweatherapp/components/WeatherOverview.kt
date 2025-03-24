package com.example.myweatherapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import com.example.myweatherapp.data.CurrentWeather
import com.example.myweatherapp.data.DailyWeather
import kotlin.math.roundToInt

@Composable
fun WeatherOverview(currentWeather: CurrentWeather, dailyWeather: DailyWeather) {
    Column (
        Modifier
            .padding(top = 16.dp)
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp)) // Round the corners
            .background(Color(0, 173, 181)) // Change background color
    ) {
        Text(text = "Weather Overview", modifier = Modifier.padding(16.dp), fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            Text(text = "${currentWeather.temperature.roundToInt()}°C", fontSize = 128.sp, letterSpacing = (-14).sp)
            Row {
                Text(text = "Max: ${dailyWeather.temperature_2m_max[0].roundToInt()}°C", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = "Min: ${dailyWeather.temperature_2m_min[0].roundToInt()}°C", fontSize = 16.sp)
            }
        }

    }
}
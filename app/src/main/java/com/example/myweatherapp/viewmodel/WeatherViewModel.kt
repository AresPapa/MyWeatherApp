package com.example.myweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.CurrentWeather
import com.example.myweatherapp.data.DailyWeather
import com.example.myweatherapp.data.HourlyWeather
import com.example.myweatherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _currentWeather = MutableStateFlow<CurrentWeather?>(null)
    val currentWeather: StateFlow<CurrentWeather?> = _currentWeather

    private val _hourlyWeather = MutableStateFlow<HourlyWeather?>(null)
    val hourlyWeather: StateFlow<HourlyWeather?> = _hourlyWeather

    private val _dailyWeather = MutableStateFlow<DailyWeather?>(null)
    val dailyWeather: StateFlow<DailyWeather?> = _dailyWeather

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = weatherRepository.getWeatherForLocation(latitude, longitude)
                _currentWeather.value = response.current_weather
                _hourlyWeather.value = response.hourly
                _dailyWeather.value = response.daily
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
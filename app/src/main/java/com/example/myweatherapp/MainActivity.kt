package com.example.myweatherapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.myweatherapp.repository.WeatherRepository
import com.example.myweatherapp.screens.WeatherScreen
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import com.example.myweatherapp.viewmodel.WeatherViewModel
import com.example.myweatherapp.viewmodel.WeatherViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.getValue

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                val context = LocalContext.current

                val locationManager = remember {
                    LocationManager(
                        context = context,
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                            context
                        )
                    )
                }

                val locationPermissions = rememberMultiplePermissionsState(
                    permissions = listOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )

                var location by remember {
                    mutableStateOf<Location?>(null)
                }

                val viewModel: WeatherViewModel by viewModels {
                    WeatherViewModelFactory(weatherRepository)
                }

                LaunchedEffect(Unit) {
                    if (!locationPermissions.allPermissionsGranted || locationPermissions.shouldShowRationale) {
                        locationPermissions.launchMultiplePermissionRequest()
                    } else {
                        location = locationManager.getLocation()
                        location?.let {
                            viewModel.fetchWeather(it.latitude, it.longitude)
                        }
                    }
                }
                    WeatherScreen(viewModel=viewModel)
                }
            }
        }
    }

val weatherRepository = WeatherRepository()

class LocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    suspend fun getLocation(): Location? {

        val hasGrantedFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasGrantedCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as android.location.LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasGrantedCoarseLocationPermission || hasGrantedFineLocationPermission)) {
            return null
        }

        return suspendCancellableCoroutine { cont ->

            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    cont.resume(task.result)
                } else {
                    cont.resume(null)
                }
            }
        }

    }

}


package com.example.nileassociate.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.tasks.await


@Composable
fun MapsComponent(){
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var isMyLocationEnabled by remember { mutableStateOf(false) }
    var isPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPositionState = rememberCameraPositionState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            isPermissionGranted = true
            isMyLocationEnabled = true
        }
    }

    LaunchedEffect(isPermissionGranted) {
        if (isPermissionGranted) {
            isMyLocationEnabled = true
            try {
                val location: Location? = fusedLocationClient.lastLocation.await()
                location?.let {
                    currentLocation = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation!!, 15f)
                }
            } catch (e: Exception) {
                // Handle exception (e.g., location is not available)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isPermissionGranted) {
            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }) {
                Text("Request Location Permission")
            }
        }
        if (isPermissionGranted) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = isMyLocationEnabled)
            ) {
                currentLocation?.let { location ->
                    Marker(
                        state = rememberMarkerState(position = location),
                        title = "Current Location"
                    )
                }
            }
        }
    }
}
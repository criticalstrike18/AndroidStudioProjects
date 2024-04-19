package com.example.cropwise.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.model.Locationdata
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale


class LocationUtils(val context : Context) {

    private val _fusedLocationClient : FusedLocationProviderClient = LocationServices.
            getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: AuthViewModel){
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation?.let {
                    val location =  Locationdata(latitude = it.latitude, longitude = it.longitude)
                    viewModel.updateLocation(location)
                }
                _fusedLocationClient.removeLocationUpdates(this)
            }
        }
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,1000).build()
        _fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())

    }
//    fun hasLocationPermission(context: Context): Boolean {
//        return ContextCompat.checkSelfPermission(
//                context, Manifest.permission.ACCESS_FINE_LOCATION) ==
//            PackageManager.PERMISSION_GRANTED
//                &&
//                ContextCompat.checkSelfPermission(
//                    context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED
//    }


}
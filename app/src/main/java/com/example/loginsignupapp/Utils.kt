package com.example.loginsignupapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat


    var longitude: Double = 0.0
    var latitude: Double = 0.0


fun getCurrentLocation(context: Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    if (hasGps) {

        if (hasGps) {
            Log.d("Utils ", "hasGps")
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                object :
                    LocationListener {


                    override fun onLocationChanged(location: Location) {

                        longitude = location.longitude
                        latitude = location.latitude

                        Log.d(
                            "Utils",
                            "GPS Latitude : $latitude"
                        )
                        Log.d(
                            "Utils",
                            " GPS Longitude : $longitude"
                        )
                    }

                    override fun onStatusChanged(
                        provider: String?,
                        status: Int,
                        extras: Bundle?
                    ) {

                    }

                })
        }


    } else {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}
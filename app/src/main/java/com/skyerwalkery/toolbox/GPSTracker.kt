package com.skyerwalkery.toolbox

/*
* code source
* https://stackoverflow.com/questions/21085497/how-to-use-android-locationmanager-and-listener
 */

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log


private val TAG: String = GPSTracker::class.java.simpleName

class GPSTracker(private val mContext: Context) : Service(), LocationListener {
    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    val canGetLocation: Boolean
    get(){
        locationManager = mContext
            .getSystemService(LOCATION_SERVICE) as LocationManager
        // getting GPS status
        isGPSEnabled = locationManager
            ?.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ?: false
        // getting network status
        isNetworkEnabled = locationManager
            ?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ?: false
        return isGPSEnabled || isNetworkEnabled
    }

    // Declaring a Location Manager
    private var locationManager: LocationManager? = null

    var location : Location? = null
        @SuppressLint("MissingPermission")
    get(){
        try {
            if(!canGetLocation)
                return null

            // First get location from Network Provider
            if (isNetworkEnabled) {
                locationManager!!.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                )
                Log.d(TAG, "Network Enabled")
                if (locationManager != null) {
                    field = locationManager!!
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled && field == null) {
                locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                )
                Log.d(TAG, "GPS Enabled")
                if (locationManager != null) {
                    field = locationManager!!
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return field
    }

    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(mContext)

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings")

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")

        // On pressing Settings button
        alertDialog.setPositiveButton(
            "Settings"
        ) { dialog, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
            dialog.cancel()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onLocationChanged(location: Location) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        // 10 seconds
        private const val MIN_TIME_BW_UPDATES = (
                1000 * 10
                ).toLong()
    }


}
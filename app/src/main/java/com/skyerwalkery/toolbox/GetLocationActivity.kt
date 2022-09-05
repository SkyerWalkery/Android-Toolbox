package com.skyerwalkery.toolbox

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.skyerwalkery.toolbox.databinding.ActivityGetLocationBinding
import kotlin.math.abs


private val TAG: String = GetLocationActivity::class.java.simpleName

enum class LocationState{
    UNKNOWN, ERROR, OK
}

class GetLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetLocationBinding
    private val getLocationViewModel: GetLocationViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Toast.makeText(
                    this,
                    getString(R.string.access_location_allowed),
                    Toast.LENGTH_SHORT
                ).show()
            } else -> {
            // No location access granted.
                Toast.makeText(
                    this,
                    getString(R.string.access_location_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private lateinit var tracker: GPSTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tracker = GPSTracker(this)

        // try retrieving info from ViewModel
        if(getLocationViewModel.lastLocInfo != null)
            updateLocationTextView(getLocationViewModel.lastLocInfo!!)
        else
            updateLocationTextView(LocationState.UNKNOWN)
    }

    override fun onDestroy() {
        super.onDestroy()
        tracker.stopUsingGPS()
        getLocationViewModel.lastLocInfo = binding.locationInfo.text as String?
    }

    @SuppressLint("MissingPermission")
    fun refreshOnClick(view: View){
        // if not allowed to access location, request for it
        if(!isFineLocationPermissionGranted()) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            updateLocationTextView(LocationState.ERROR)
            return
        }

        // check whether gps is on
        if(!tracker.canGetLocation){
            Toast.makeText(
                this,
                getString(R.string.need_gps),
                Toast.LENGTH_SHORT
            ).show()
            updateLocationTextView(LocationState.ERROR)
            return
        }

        // get location and update info
        val loc: Location? = tracker.location
        if(loc == null){
            Log.d(TAG, "get null location")
            updateLocationTextView(LocationState.ERROR)
            return
        }
        val latitude: Double  = loc.latitude
        val longitude: Double = loc.longitude
        updateLocationTextView(LocationState.OK, latitude, longitude)
    }

    // update textview by location
    private fun updateLocationTextView(state: LocationState, latitude: Double = 0.0, longitude: Double = 0.0){
        when (state) {
            LocationState.ERROR -> {
                val locInfo: String = getString(R.string.location_error)
                binding.locationInfo.text = locInfo
            }
            LocationState.UNKNOWN -> {
                val locInfo: String = getString(R.string.location_unknown)
                binding.locationInfo.text = locInfo
            }
            else -> {
                val locInfo: String = String.format(
                    getString(R.string.location_info),
                    abs(latitude),
                    if (latitude == 0.0){ 0.toChar() }
                    else if(latitude > 0.0){ 'N' }
                    else { 'S' },
                    abs(longitude),
                    if (longitude == 0.0){ 0.toChar() }
                    else if(longitude > 0.0){ 'E' }
                    else { 'W' },
                )
                binding.locationInfo.text = locInfo
            }
        }
    }

    // update textview's text directly
    private fun updateLocationTextView(text: String){
        binding.locationInfo.text = text
    }

    private fun isFineLocationPermissionGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

}
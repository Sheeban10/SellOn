package com.example.sellon.ui.splash

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.sellon.BaseActivity
import com.example.sellon.MainActivity
import com.example.sellon.R
import com.example.sellon.ui.login.LoginActivity
import com.example.sellon.utilities.Constants
import com.example.sellon.utilities.SharedPref
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.*

@Suppress("DEPRECATION")
class SplashActivity: BaseActivity() {

    private val MY_PERMISSION_REQUEST_LOACATION = 100
    private val REQUEST_GPS = 101
    private var locationRequest : LocationRequest? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        askforPermissions()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLocationCallback()
    }



    override fun onResume() {
        super.onResume()


    }
    private fun askforPermissions() {
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_REQUEST_LOACATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSION_REQUEST_LOACATION){
            var granted = false
            for(grantResult in grantResults){
                if ( grantResult == PackageManager.PERMISSION_GRANTED){
                    granted = true
                }
            }
            if (granted)
                enableGPS()
        }
    }
    private fun enableGPS(){
        locationRequest = LocationRequest.create()
        locationRequest?.setInterval(3000)
        locationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest!!)

        val task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())

        task.addOnCompleteListener(object : OnCompleteListener<LocationSettingsResponse>{
            override fun onComplete(p0: Task<LocationSettingsResponse>) {
                try {
                    val response = task.getResult(ApiException::class.java)
                    startLocationUpdates()
                }catch (exception :ApiException){
                    when(exception.statusCode){

                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED->{
                            val resolvable = exception as ResolvableApiException
                            resolvable.startResolutionForResult(this@SplashActivity, REQUEST_GPS )
                        }
                    }
                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GPS){
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(LocationRequest(), locationCallback, null)
    }

    private fun getLocationCallback() {
        locationCallback = object :LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val location = p0.lastLocation

                SharedPref(this@SplashActivity).setString(Constants.CITY_NAME, getCityName(location))

                if (SharedPref(this@SplashActivity).getString(Constants.USER_ID)?.isEmpty()!!) {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun getCityName(location : Location?): String {
        var cityName =""
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val address = geocoder.getFromLocation(location!!.latitude, location?.longitude!!,1)

            cityName = address?.get(0)!!.locality.toString()
        }catch (e:IOException){
            Log.d("locationexception", "failed")
        }
        return cityName
    }


}




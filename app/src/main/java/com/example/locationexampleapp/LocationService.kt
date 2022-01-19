package com.example.locationexampleapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.concurrent.TimeUnit

class LocationService : Service() {

    private val fusedLocation by lazy {
        LocationServices.getFusedLocationProviderClient(baseContext)
    }
    private var lat : Double = 0.0
    private var lon : Double = 0.0

    private var locationRequest: LocationRequest? = null

    private var locationCallBack: LocationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            for(loc in p0.locations){
                Log.d("LOCATIONS","Location Result: "+loc.toString())
                lat = loc.latitude
                lon = loc.longitude
                //MapsActivity.updateMap( MapsActivity.mMap,lat, lon)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        locationRequest = LocationRequest.create()
        locationRequest?.interval = TimeUnit.SECONDS.toMillis(30)
        locationRequest?.fastestInterval = TimeUnit.SECONDS.toMillis(30)
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //We start our service and put all location logic
        // todo add logic to request location every 30 seconds

        intent?.let {
            //fusedLocation.requestLocationUpdates(locationRequest, getPendingIntent())
            fusedLocation.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())
            /*fusedLocation.lastLocation.addOnSuccessListener { location ->
                if (location!=null){
                    lat = location.latitude
                    lon = location.longitude
                    Log.d("LOCATION", "Lat: "+lat+" "+"Long: "+lon)
                }
            } */

            startForeground(3, intent.getParcelableExtra(NOTIFICATION_KEY))
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocation.removeLocationUpdates(locationCallBack)
    }

    private fun getPendingIntent(): PendingIntent? {
        // todo create a broadcast receiver as LocationReceiver
        // here you need to define your broadcast receiver for LocationReceiver
        // the broadcast will receive the location and from there you will be able to do any task on it
        Intent(baseContext, LocationReceiver::class.java).apply {
            return PendingIntent.getBroadcast(baseContext, 2, this, PendingIntent.FLAG_CANCEL_CURRENT)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object{
        private const val NOTIFICATION_KEY = "NOTIFICATION"

        //Function to start a service from any other class
        fun startService(context: Context, foregroundNotification: Notification){
            //Create intent to start location service
            Intent(context, LocationService::class.java).apply {
                putExtra(NOTIFICATION_KEY, foregroundNotification)
                context.startService(this)
            }
        }

        //Allow us to stop location service, intent needed to specify service to try to stop
        fun stopService(context: Context){
            val stopIntent = Intent(context, LocationService::class.java)
            context.stopService(stopIntent)
        }
    }
}
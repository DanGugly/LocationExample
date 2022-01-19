package com.example.locationexampleapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class LocationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Get current location
        /*fusedLocation.lastLocation.addOnSuccessListener { location ->
            if (location!=null){
                var lat = location.latitude
                var lon = location.longitude
                Log.d("LOCATION", "Lat: "+lat+" "+"Long: "+lon)
            }
        } */
    }
}
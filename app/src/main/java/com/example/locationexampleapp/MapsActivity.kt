package com.example.locationexampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.locationexampleapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var lat : Double = 0.0
    private var lon : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // todo pass the location from the LocationReceiver to this method in order to show the location in map
        lat = intent.getDoubleExtra("lat", 0.0)
        lon = intent.getDoubleExtra("lon", 0.0)
        Log.d("MapsAc", "Values: "+lat+" "+lon)
        // Add a marker in Sydney and move the camera
        val newLoc = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(newLoc).title("Marker in New Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc))
    }
    fun updateMap(googleMap: GoogleMap, lat: Double, lon: Double) {
        mMap = googleMap
        val newLoc = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(newLoc).title("Marker in New Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc))
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(17f))
    }
}
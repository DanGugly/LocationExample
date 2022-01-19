package com.example.locationexampleapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.locationexampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Creating binding variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Assign value to lateinit binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Set the view into constraint layout
        setContentView(binding.root)

        //Check permissions in oncreate
        checkForPermissions(
            arrayOf(
                //Constructing permission arrow
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        createNotificationChannel(baseContext)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        //Setting start tracking location on tap
        binding.startTracking.setOnClickListener{
            LocationService.startService(baseContext, createNotification(baseContext))
        }

        //Set stop tracking to stop service
        binding.stopTracking.setOnClickListener{
            LocationService.stopService(baseContext)
        }

        binding.showMap.setOnClickListener{
            Intent(baseContext, MapsActivity::class.java).apply {
                // This way doesnt require a local variable to be made before starting the activity i.e. saves SPACE!
                putExtra("lat", 37.421998)
                putExtra("lon", -122.084000)
                startActivity(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //Here we check the request code
        if(requestCode == REQUEST_CODE){
            //iterate grant results for int array
            grantResults.forEach { granted ->  //can also use it
                //Check empty and granted values
                if (grantResults.isNotEmpty() && granted==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(baseContext, "Permission granted", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(baseContext, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkForPermissions(permissions: Array<String>){

        //ActivityCompat
         //   .requestPermissions(this, permissions, 2)
        //Check list of permissions before asking user for permission
        for (permission in permissions){
            //Check form context compat for the permission
            val granted = ContextCompat.checkSelfPermission(baseContext, permission)
            if (granted == PackageManager.PERMISSION_DENIED){
                //Request or ask permission for permission
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
            } else{
                // If granted display a toast
                Toast.makeText(baseContext, "Permissions Granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        private const val REQUEST_CODE = 21
    }

}
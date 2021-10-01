package com.example.weatherappkotlin.ui.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.example.weatherappkotlin.ui.main.GoogleMapsFragment
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.MainActivityBinding
import java.io.IOException


private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class MainActivity : AppCompatActivity() {

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> getContact()
                shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_CONTACTS
                )
                -> {
                    Toast.makeText(
                        this,
                        " Go to app settings and permissions ",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(this, " Fail ", Toast.LENGTH_LONG).show()
            }
        }
    private val permissionGeoResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        when{
            result -> getLocation()
            shouldShowRequestPermissionRationale
                (this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Toast.makeText(
                    this,
                    " Go to app settings and permissions ",
                    Toast.LENGTH_LONG)
                    .show()
            }
            else -> Toast.makeText(this, " Fail ", Toast.LENGTH_LONG).show()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        locationManager.getProvider(LocationManager.GPS_PROVIDER)?. let {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                REFRESH_PERIOD,
                MINIMAL_DISTANCE,
                object: LocationListener {

                    override fun onLocationChanged(location: Location) {
                        getAddressByLocation(location)
                    }

                    override fun onStatusChanged(
                        provider: String?,
                        status: Int,
                        extras: Bundle?
                    ){
                    // do nothing
                     }

                    override fun onProviderEnabled(provider: String) {
                        super.onProviderEnabled(provider)
                    }

                    override fun onProviderDisabled(provider: String) {
                        super.onProviderDisabled(provider)
                    }

                }
            )
        }

    } else {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                getAddressByLocation(it)
            } ?: {/* ... */}
        }
    }

    private fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(this)

        Thread {
            try {
                val address = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                binding.container.post{
                    AlertDialog.Builder(this)
                        .setMessage(address[0].getAddressLine(0))
                        .setCancelable(true)
                        .show()
                }

            } catch (e: IOException){
                e.printStackTrace()
            }

        }.start()

    }

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

 //   @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.idHistory -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container,HistoryFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.getContacts -> {
                permissionResult.launch(android.Manifest.permission.READ_CONTACTS)
                true
            }
            R.id.getLocation -> {
                permissionGeoResult.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                true
            }

            R.id.showMaps -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, GoogleMapsFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    @SuppressLint("Range")
    private fun getContact() {
         val cursor : Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

          val contacts =  mutableListOf<String>()
              cursor?.let {
              for (i in 0..cursor.count) {
              if (cursor.moveToPosition(i)) {
                 val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                  contacts.add(name)
             }
         }
            it.close()
     }
        AlertDialog.Builder(this)
            .setItems(contacts.toTypedArray()){ _, _ ->  }
            .setCancelable(true)
            .show()
    }
}
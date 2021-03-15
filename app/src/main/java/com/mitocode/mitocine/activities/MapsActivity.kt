package com.mitocode.mitocine.activities

import android.Manifest
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mitocode.mitocine.GPSTracker
import com.mitocode.mitocine.R
import com.mitocode.mitocine.databinding.ActivityMapsBinding
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityMapsBinding

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //REFERENCIA AL R.id.map (fragmento) y lo settea como SupportMapFragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //acciones

        binding.search.setOnClickListener {
            val address = binding.address.text.toString()
            val geocoder = Geocoder(this)
            val addressList = geocoder.getFromLocationName(address, 1)
            if (addressList.size>0)
            {
                val latitude = addressList[0].latitude
                val longitude = addressList[0].longitude
                val latLng = LatLng(latitude, longitude)

                //settear textos
                binding.latitude.setText(latitude.toString())
                binding.longitude.setText(longitude.toString())
                //animar camar
                mMap.addMarker(
                    MarkerOptions().position(latLng).snippet("Descripcion: $address")
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
        binding.getPosition.setOnClickListener {
            requiredPermissions()
        }
    }
        companion object{
            const val RC_LOCATION=900
        }
    @AfterPermissionGranted(RC_LOCATION)
    private fun requiredPermissions() {
        val perms =
            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
            val gpsTracker = GPSTracker(this)
            if (gpsTracker.canGetLocation())
            {
                val latitude = gpsTracker.latitude
                val longitude = gpsTracker.longitude
                //pintar en el mapa los extras
                mMap.addMarker(
                    MarkerOptions().position(LatLng(latitude, longitude)).title("Ubicación actual").snippet("Ubicación actual")
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15f))
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, getString(R.string.location_rationale),
                RC_LOCATION, *perms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Marcador en Sydney
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marcador en Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 9f))

        //dibujar poligonos en el Mapa
        /*
        mMap.addPolygon(PolygonOptions()
            .add(LatLng(-34.0,151.0))
            .add(LatLng(-35.0,151.0))
            .add(LatLng(-36.0,151.0))
            .add(LatLng(-34.0,152.0))
            .add(LatLng(-34.0,153.0))
            .add(LatLng(-34.0,154.0))
            .fillColor(Color.BLUE)
        )
        */

        //dibujar circulos
        mMap.addCircle(
            CircleOptions()
                .visible(true)
                .center(LatLng(-33.0, 140.0))
                .strokeColor(Color.RED)
                .radius(2000.0)
                .strokeWidth(5f)
                .fillColor(Color.YELLOW)
        )

        //dibujar poligono
        mMap.addPolyline(
            PolylineOptions()
                .add(LatLng(-30.0, 151.0))
                .add(LatLng(-32.0, 151.0))
                .color(Color.GREEN)
                .width(8f)
        )
        //recibir los extras
        if (intent.hasExtra("name")){
            val name = intent.getStringExtra("name")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val address = intent.getStringExtra("address")
            //pintar en el mapa los extras
            mMap.addMarker(
                MarkerOptions().position(LatLng(latitude, longitude)).title(name).snippet(
                    address
                )
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15f))
        }

    }
}



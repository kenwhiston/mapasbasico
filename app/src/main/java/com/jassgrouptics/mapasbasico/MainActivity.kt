package com.jassgrouptics.mapasbasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),
    OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener,
    GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveStartedListener{


    /**
     * Referencia al mapa
     */
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inflar mapa de la vista
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        //call al util de mapa
        OnMapAndViewReadyListener(mapFragment, this)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        /**
         * sino esta disponible se regresa al proceso principal
         */
        map = googleMap ?: return

        /**
         * asignamos y configuramos el mapa
         */
        with(map) {
            //zoom
            uiSettings.isZoomControlsEnabled = true

            //tipo de mapa
            mapType = GoogleMap.MAP_TYPE_NORMAL

            // Override the default content description on the view, for accessibility mode.
            // Ideally this string would be localised.
            setContentDescription("Demo Mapa Basico GDG Chimbote")

            /**
             * eventos sobre mapa
             */
            setOnMapClickListener(this@MainActivity)
            setOnMapLongClickListener(this@MainActivity)
            setOnCameraIdleListener(this@MainActivity)
            setOnCameraMoveStartedListener(this@MainActivity)

        }

        //agregamos marcador
        addMarker()

    }


    /**
     * metodo que agrega un marcador al mapa
     */
    fun addMarker(){
        //These coordinates represent the latitude and longitude of the Googleplex.

        //posicion latitud y longitud
        val latitude = -9.121819
        val longitude =  -78.531130


        //nivel de zoom
        val zoomLevel = 18f

        val homeLatLng = LatLng(latitude, longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        //map.addMarker(MarkerOptions().position(homeLatLng))

        /**
         * mayor personalizacion de marker
         */

        //nuevo icono
        val icono = BitmapDescriptorFactory.fromResource(R.drawable.ic_icono_vacuna_marker)

        var marker = map.addMarker(
            com.google.android.gms.maps.model.MarkerOptions()
                .icon(icono)
                .position(homeLatLng)
                .title("Plaza Mayor")
                .snippet("La más grande del norte del país")
                .infoWindowAnchor(0.5F, 0F)
                .draggable(false)
                .zIndex(0F))

    }

    override fun onMapClick(p0: LatLng?) {
        if(!::map.isInitialized) return

        val contextView = findViewById<View>(R.id.layout)
        Snackbar.make(contextView, "onMapClick POINT(${p0!!.latitude} , ${p0!!.longitude})", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onMapLongClick(p0: LatLng?) {
        if(!::map.isInitialized) return

        val contextView = findViewById<View>(R.id.layout)
        Snackbar.make(contextView, "onMapLongClick POINT(${p0!!.latitude} , ${p0!!.longitude})", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCameraIdle() {
        if(!::map.isInitialized) return

        val contextView = findViewById<View>(R.id.layout)
        Snackbar.make(contextView, "onCameraIdle POINT(${map.cameraPosition.target.latitude} , ${ map.cameraPosition.target.longitude})", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCameraMoveStarted(p0: Int) {
        if(!::map.isInitialized) return
        Log.v("MapasBasico","onCameraMoveStarted POINT(${map.cameraPosition.target.latitude} , ${ map.cameraPosition.target.longitude})")
    }

}
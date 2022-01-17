package com.aymn.knowmeapp.persons.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aymn.knowmeapp.ViewModelFactory
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint
import java.util.*


class MapsFragment : Fragment() , OnMapReadyCallback {
    private var binding: FragmentMapsBinding? = null
    private var map: GoogleMap? = null
    private var locationLatt :String? = ""
    private var locationLong :String? = ""
    private val viewModel: PersonViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val navigationArgs: MapsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.topAppBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.saveLocation ->{
                    if (locationLatt != "" && locationLong != ""){
                        val id = navigationArgs.id
                        val actionloac = MapsFragmentDirections.actionMapsFragmentToParsoneInfoFragment(id = id,navigationArgs.name,
                            locationLatt.toString(),locationLong.toString())
                        findNavController().navigate(actionloac)}
                    else {
                        Toast.makeText(context, "please choose your friend location", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.normal_map -> {
                    map?.mapType = GoogleMap.MAP_TYPE_NORMAL
                    true
                }
                R.id.hybrid_map -> {
                    map?.mapType = GoogleMap.MAP_TYPE_HYBRID
                    true
                }
                R.id.satellite_map -> {
                    map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    true
                }
                R.id.terrain_map -> {
                    map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }

        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding?.currentLocation?.setOnClickListener {
            if (allPermissionsGranted()) {
                map?.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                    )
                }

            }
        }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map?.setOnMapClickListener { latLng ->
            map?.clear()
            // A Snippet is Additional text that's displayed below the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            locationLatt = latLng.latitude.toString()
            locationLong = latLng.longitude.toString()
            Log.e("TAG", "onViewCreated: ${latLng.latitude}")
            Log.e("TAG", "onViewCreated: ${latLng.longitude}")

            map?.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
        }

    }


    // Checks that users have given permission
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this.requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                map?.isMyLocationEnabled = true

            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                this.requireActivity().finish()
            }
        }
    }

companion object {
    private const val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
}


}
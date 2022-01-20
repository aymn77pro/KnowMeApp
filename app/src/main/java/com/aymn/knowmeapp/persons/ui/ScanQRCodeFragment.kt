package com.aymn.knowmeapp.persons.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.aymn.knowmeapp.ViewModelFactory
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.knowmeapp.databinding.FragmentScanQRCodeBinding
import kotlinx.coroutines.launch


class ScanQRCodeFragment : Fragment() {

    private val viewModel:PersonViewModel by activityViewModels {
        ViewModelFactory()
    }

 private var _binding:FragmentScanQRCodeBinding? = null
    val binding get() = _binding

    private lateinit var codesScanner : CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanQRCodeBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().checkSelfPermission(Manifest.permission.CAMERA) ==
               PackageManager.PERMISSION_DENIED ){
            this.requestPermissions(arrayOf(Manifest.permission.CAMERA),123)
        }else{
            startScanner()
        }


}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context, "camra permission GRANTED", Toast.LENGTH_SHORT).show()
                startScanner()
            }else{
                Toast.makeText(context, "camra permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codesScanner.isInitialized){
            codesScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::codesScanner.isInitialized){
            codesScanner.releaseResources()
        }
    }

    private fun startScanner() {
        codesScanner = CodeScanner(requireContext(),binding?.scannerView!!)
       codesScanner.camera = CodeScanner.CAMERA_BACK
       codesScanner.formats = CodeScanner.ALL_FORMATS

       codesScanner.autoFocusMode = AutoFocusMode.SAFE
       codesScanner.scanMode = ScanMode.SINGLE
       codesScanner.isAutoFocusEnabled = true
       codesScanner.isFlashEnabled = false

            codesScanner.decodeCallback = DecodeCallback {
                lifecycleScope.launch {
                    Toast.makeText(context, "scan Result : ${it.text}", Toast.LENGTH_LONG).show()

                }
            }


        codesScanner.errorCallback = ErrorCallback {
            lifecycleScope.launch {
                Toast.makeText(
                    context,
                    "camera initialization : $${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding?.scannerView!!.setOnClickListener {
            codesScanner.startPreview()
        }
    }

}

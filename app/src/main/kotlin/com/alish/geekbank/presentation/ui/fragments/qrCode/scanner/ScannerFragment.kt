package com.alish.geekbank.presentation.ui.fragments.qrCode.scanner

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentScannerBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.budiyev.android.codescanner.*
import com.google.android.material.snackbar.Snackbar


class ScannerFragment :
    BaseFragment<ScannerViewModel, FragmentScannerBinding>(R.layout.fragment_scanner) {

    override val binding by viewBinding(FragmentScannerBinding::bind)
    override val viewModel: ScannerViewModel by viewModels()
    private var codeScanner: CodeScanner? = null

    override fun setupListeners() {
        checkPermissions()
    }

    private fun checkPermissions() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.CAMERA
                )
            } ==
            PackageManager.PERMISSION_GRANTED
        ) {
            initiateScan()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.CAMERA),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
                initiateScan()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initiateScan() {
        val scannerView = binding.codeScannerView
        codeScanner = context?.let { CodeScanner(it, scannerView) }

        codeScanner?.camera = CodeScanner.CAMERA_BACK
        codeScanner?.formats = CodeScanner.ALL_FORMATS

        codeScanner?.apply {
            isAutoFocusEnabled = true
            isFlashEnabled = false
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
        }
        codeScanner!!.decodeCallback = DecodeCallback {
            runOnUiThread {
                Snackbar.make(scannerView, "Scan result: ${it.text}", 5000).show()
            }
        }
        codeScanner?.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(
                    context, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner!!.startPreview()
        }
    }

    private fun runOnUiThread(function: () -> Unit) {
    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }
}
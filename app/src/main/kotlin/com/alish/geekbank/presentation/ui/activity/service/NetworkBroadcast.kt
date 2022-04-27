package com.alish.geekbank.presentation.ui.activity.service

import android.app.AlertDialog
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import com.alish.geekbank.databinding.NoInternetDialogBinding

class NetworkBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!isNetworkConnected(context)) {
            val binding: NoInternetDialogBinding = NoInternetDialogBinding.
            inflate(LayoutInflater.from(context))

            val builder = AlertDialog.Builder(context)
            builder.setView(binding.getRoot())
            builder.setCancelable(false)
            val dialog: Dialog = builder.create()
            dialog.show()

            binding.btnTryAgain.setOnClickListener(View.OnClickListener {
                if (isNetworkConnected(context)) {
                    dialog.dismiss()
                }
            })
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

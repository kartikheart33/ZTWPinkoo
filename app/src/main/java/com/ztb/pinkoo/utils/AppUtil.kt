package com.ztb.pinkoo.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import com.ztb.pinkoo.R

class AppUtil {

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
        return result
    }



    //................show no network alert dialog............
    fun internetDialog(context: Activity, message: String) {
        /* AlertDialog.Builder(context, R.style.AlertDialogTheme)
             .setMessage(message)
             .setCancelable(false)
             .setPositiveButton("Ok") { dialog, id ->
                 dialog.dismiss()
             }.setTitle("No Internet Connection").show()*/

        val dialog: AlertDialog
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        val inflater: LayoutInflater = context.layoutInflater
        val customLayout: View = inflater.inflate(R.layout.internet_dialog_wfs, null)
        builder.setView(customLayout)

        val messsagetext: TextView = customLayout.findViewById(R.id.messagetext)
        val okbtn: Button = customLayout.findViewById(R.id.okbtn)
        messsagetext.text = message


        dialog = builder.create()
        val window = dialog.getWindow()
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp

        okbtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }


    var dialog: Dialog? = null

    //show progress dialod
    fun AlertDialogValidation(context: Activity, message: String) {
        val dialog: AlertDialog
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        val inflater: LayoutInflater = context.getLayoutInflater()
        val customLayout: View = inflater.inflate(R.layout.dialog_validation_wfs, null)
        builder.setView(customLayout)
        val messsagetext: TextView
        val okbtn: Button

        messsagetext = customLayout.findViewById(R.id.messagetext)
        okbtn = customLayout.findViewById(R.id.okbtn)
        messsagetext.text = message


        dialog = builder.create()
        val window = dialog.getWindow()
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp

        okbtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.setCanceledOnTouchOutside(true)

    }


    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}
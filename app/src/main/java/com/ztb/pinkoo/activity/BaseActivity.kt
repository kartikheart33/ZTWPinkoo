package com.ztb.pinkoo.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.visaero.utils.internet.ConnectivityObserver
import com.visaero.utils.internet.NetworkConnectivityObserver
import com.ztb.pinkoo.Interface.InternetAvailabilityListener
import com.ztb.pinkoo.dialogs.ProgressDialog
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    companion object {
        private var TAG = BaseActivity::class.java.name
    }

    override fun onPause() {
        super.onPause()
        Log.e("BaseActivity - onPause", this.localClassName)

    }

    override fun onResume() {
        super.onResume()
        Log.e("BaseActivity - onResume", this.localClassName)


    }

    override fun onRestart() {
        super.onRestart()

    }




}
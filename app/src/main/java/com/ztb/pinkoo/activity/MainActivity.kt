package com.ztb.pinkoo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ztb.pinkoo.Factory.HomeFactory
import com.ztb.pinkoo.R
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.ViewModel.HomeViewModel
import com.ztb.pinkoo.api.ApiInterface
import com.ztb.pinkoo.databinding.ActivityMainBinding
import com.ztb.pinkoo.utils.AppConstants
import com.ztb.pinkoo.utils.AppUtil
import com.ztb.pinkoo.utils.SharedPreferenceUtil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferencesUtils: SharedPreferenceUtil
    private var appUtil = AppUtil()
    lateinit var viewModel: HomeViewModel
    lateinit var apiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorsecond)

        sharedPreferencesUtils = SharedPreferenceUtil(this)
        apiInterface = ApiInterface.create(
            sharedPreferencesUtils.getStringPreferences(AppConstants.LOGIN_TOKEN).toString()
        )
        viewModel = ViewModelProvider(
            this,
            HomeFactory(HomeRepository(apiInterface))
        ).get(HomeViewModel::class.java)

    }
}
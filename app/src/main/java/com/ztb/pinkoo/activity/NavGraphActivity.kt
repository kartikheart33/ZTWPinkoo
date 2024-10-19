package com.ztb.pinkoo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.ztb.pinkoo.R
import com.ztb.pinkoo.databinding.ActivityNavGraphBinding
import com.ztb.pinkoo.utils.setWindowStatusBarColor

class NavGraphActivity : BaseActivity() {
    private lateinit var navController: NavController
    lateinit var binding: ActivityNavGraphBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setWindowStatusBarColor(R.color.colorPrimary)
        binding = ActivityNavGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_page)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home_page) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(addDestinationListener)

    }
    private val addDestinationListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {

                R.id.navigation_home -> {
                }

                R.id.navigation_detail -> {

                }

                else -> {

                }
            }

        }
}
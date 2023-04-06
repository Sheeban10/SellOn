package com.example.sellon

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sellon.databinding.ActivityLoginBinding
import com.example.sellon.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController (R.id.nav_host_fragment)


        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )

        setupActionBarWithNavController (navController, appBarConfiguration)
        navView.setupWithNavController (navController)

    }
}
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navController = findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//

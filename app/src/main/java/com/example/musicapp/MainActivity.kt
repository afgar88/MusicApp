package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private  val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        MusicApp.musicComponent.inject(this)

        navegation()


    }

    /**
     * this function is on charge to navegation between fragments 
     */

    fun navegation() {

     setContentView(binding.root)

      val navView: BottomNavigationView = binding.activityMainBottomNavigationView
      val navController = findNavController(R.id.activity_main_nav_host_fragment)
      // Passing each menu ID as a set of Ids because each
       // menu should be considered as top level destinations.
     val appBarConfiguration = AppBarConfiguration(
        setOf(
              R.id.rockFragment, R.id.classicFragment, R.id.popFragment
         )
       )
      setupActionBarWithNavController(navController, appBarConfiguration)
      navView.setupWithNavController(navController)
    }
}
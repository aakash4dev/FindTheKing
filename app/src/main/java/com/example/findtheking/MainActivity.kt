package com.example.findtheking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // connect with main activity
        val homeFragment = HomeFragment()
        val finalFragment = FinalFragment()

        // add the fragment to the container
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, homeFragment)
            addToBackStack(null)
            commit()
        }

    }
}

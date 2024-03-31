package com.example.findtheking

import android.os.Bundle
import android.widget.Button
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




        // get api https://findfalcone.geektrust.com/vehicles
        // sampleResponse
        /*
        [
            {
                "name": "Space pod",
                "total_no": 2,
                "max_distance": 200,
                "speed": 2
            },
            {
                "name": "Space rocket",
                "total_no": 1,
                "max_distance": 300,
                "speed": 4
            },
            {
                "name": "Space shuttle",
                "total_no": 1,
                "max_distance": 400,
                "speed": 5
            },
            {
                "name": "Space ship",
                "total_no": 2,
                "max_distance": 600,
                "speed": 10
            }
        ]
        */






    }
}

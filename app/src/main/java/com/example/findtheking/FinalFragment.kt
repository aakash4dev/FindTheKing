package com.example.findtheking

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class FinalFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_final, container, false)

        val distanceView: TextView = view.findViewById(R.id.distance)
        distanceView.text = arguments?.getInt("distance", -1).toString()

        val planetSelectedView: TextView = view.findViewById(R.id.planet_selected)
        val planetName = arguments?.getString("planetName", "Default Planet Name")
        planetSelectedView.text = planetName

        return view
    }



//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        // Inflate the layout for this fragment
//        var view = inflater.inflate(R.layout.fragment_final, container, false)
////        var rootView: View = inflater.inflate(R.layout.fragment_home, container, false)
//
//        // select id planet_selected
//        var planetSelected = view.findViewById(R.id.planet_selected)
//        // set text to planetName
//        planetSelected.text = arguments?.getString("planetName", "DefaultPlanetName")
//
//        // select id distance
//        var distance = view.findViewById(R.id.distance)
//        // set text to distance
//        distance.text = arguments?.getInt("distance", -1).toString()
//
//
//        return view ; inflater.inflate(R.layout.fragment_final, container, false)
//    }

    // back button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.back_button).setOnClickListener {
            val homeFragment = HomeFragment()
            // add the fragment to the container
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, homeFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

}
package com.example.findtheking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FinalFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_final, container, false)
    }

    // back button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.back_button).setOnClickListener {
            val homeFragment = HomeFragment()
            val finalFragment = FinalFragment()
            // add the fragment to the container
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, homeFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

}
package com.example.findtheking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//    val spinner: Spinner = view?.findViewById(R.id.spinner) as Spinner
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize the spinners here after the view is created
        val spinner1: Spinner = view.findViewById(R.id.spinnerExample1)
        val spinner2: Spinner = view.findViewById(R.id.spinnerExample2)
        val spinner3: Spinner = view.findViewById(R.id.spinnerExample3)
        val spinner4: Spinner = view.findViewById(R.id.spinnerExample4)

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.spinner_items1,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner1.adapter = adapter
            }

            spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Code to perform some action when nothing is selected
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // An item was selected. You can retrieve the selected item using
                    val item = parent?.getItemAtPosition(position).toString()
                    Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()
                }
            }

            // 2
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),

                R.array.spinner_items1,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner2.adapter = adapter
            }

            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Code to perform some action when nothing is selected
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val item = parent?.getItemAtPosition(position).toString()
                    Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()
                }
            }

            // 3
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),

                R.array.spinner_items1,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner3.adapter = adapter
            }

            spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Code to perform some action when nothing is selected
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // An item was selected. You can retrieve the selected item using
                    val item = parent?.getItemAtPosition(position).toString()
                    Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()
                }
            }


            // 4
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),

                R.array.spinner_items1,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner4.adapter = adapter
            }

            spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Code to perform some action when nothing is selected
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // An item was selected. You can retrieve the selected item using
                    val item = parent?.getItemAtPosition(position).toString()
                    Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()
                }
            }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
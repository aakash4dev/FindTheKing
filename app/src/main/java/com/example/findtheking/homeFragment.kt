package com.example.findtheking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONArray

data class Vehicle(
    val name: String,
    var total_no: Int,
    val max_distance: Int,
    val speed: Int
)

data class FetchResult(
    val success: Boolean,
    var vehicles: List<Vehicle>? = null,
    val errorMessage: String? = null
)
data class Planet(
    val name: String,
    val distance: Int
)

data class PlanetsFetchResult(
    val success: Boolean,
    val planets: List<Planet>? = null,
    val errorMessage: String? = null
)

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var APIFetchedSuccess = true

        // fetchVehicles before inflating the layout
        val fetchVehicleResult = runBlocking {
            fetchVehicles().await()
        }
        if (fetchVehicleResult.success) {
            Toast.makeText(requireContext(), "Successfully fetched vehicle data", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), "Failed to fetch vehicle data", Toast.LENGTH_LONG)
                .show()
            APIFetchedSuccess = false
        }

        // fetch planets list
        val fetchPlanetsResult = runBlocking {
            fetchPlanets().await()
        }
        if (fetchPlanetsResult.success) {
            Toast.makeText(requireContext(), "Successfully fetched planet data", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), "Failed to fetch planet data", Toast.LENGTH_LONG)
                .show()
            APIFetchedSuccess = false
        }


        // fetch token id
        val tokenId = runBlocking {
            fetchToken().await()
        }
        if (tokenId == "Error fetching token" || tokenId == "") {
            Toast.makeText(requireContext(), "Failed to fetch token", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), tokenId, Toast.LENGTH_LONG)
                .show()
        }


        val selectedPlants = mutableListOf<String>("","","","")
        val selectedVehicles = mutableListOf<String>("", "", "", "")
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)

        val planet1: Spinner = rootView.findViewById(R.id.planet1)
        val planet2: Spinner = rootView.findViewById(R.id.planet2)
        val planet3: Spinner = rootView.findViewById(R.id.planet3)
        val planet4: Spinner = rootView.findViewById(R.id.planet4)

        var isFirstSelection1 = true
        var isFirstSelection2 = true
        var isFirstSelection3 = true
        var isFirstSelection4 = true

        val adapter1: ArrayAdapter<CharSequence>
        val adapter2: ArrayAdapter<CharSequence>
        val adapter3: ArrayAdapter<CharSequence>
        val adapter4: ArrayAdapter<CharSequence>


if (!APIFetchedSuccess) {
    // set error visibility true
    val failureLayout:LinearLayout  = rootView.findViewById(R.id.failureLayout)
    failureLayout.visibility = View.VISIBLE
}else {
    val successLayout:LinearLayout  = rootView.findViewById(R.id.successLayout)
    successLayout.visibility = View.VISIBLE

     adapter1 = ArrayAdapter(requireContext(), R.layout.custom_planet, fetchPlanetsResult.planets!!.map { it.name })
     adapter2 = ArrayAdapter(requireContext(), R.layout.custom_planet, fetchPlanetsResult.planets.map { it.name })
     adapter3= ArrayAdapter(requireContext(), R.layout.custom_planet, fetchPlanetsResult.planets.map { it.name })
     adapter4 = ArrayAdapter(requireContext(), R.layout.custom_planet, fetchPlanetsResult.planets.map { it.name })

    planet1.adapter = adapter1
    planet1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        @SuppressLint("SetTextI18n")
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            idid: Long
        ) {
            if (isFirstSelection1) {
                isFirstSelection1 = false
                return
            } else {
                val item = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()

                selectedPlants[0] = item // crashing on this line

//                    selectedPlants.add(item)

                // make other groups invisible
                val radioGroup2 = rootView.findViewById<RadioGroup>(R.id.planet2_radio_group)
                radioGroup2.visibility = View.GONE
                val radioGroup3 = rootView.findViewById<RadioGroup>(R.id.planet3_radio_group)
                radioGroup3.visibility = View.GONE
                val radioGroup4 = rootView.findViewById<RadioGroup>(R.id.planet4_radio_group)
                radioGroup4.visibility = View.GONE

                // Example for setting up radio buttons for the first planet
                val radioGroup = rootView.findViewById<RadioGroup>(R.id.planet1_radio_group)
                radioGroup.removeAllViews() // Clear previous radio buttons
                radioGroup.visibility = View.VISIBLE // Make the RadioGroup visible

                fetchVehicleResult.vehicles?.forEachIndexed { index, vehicle ->
                    val radioButton = RadioButton(requireContext()).apply {
                        text = "${vehicle.name} (${vehicle.total_no})"
                        id = View.generateViewId()
                        tag = index // Use the vehicle's index as the tag for identification
                    }
                    radioGroup.addView(radioButton)
                }

                // crashing on this loop call
                // Listen for changes in radio button selection
//                radioGroup.setOnClickListener(View.OnClickListener {
//
//                    // decrease counting by one of fetchVehicleResult.vehicles[0].total_no
//                    var validVehicle = false
//                    for (i in 0 until fetchVehicleResult.vehicles!!.size) {
//                            if (fetchVehicleResult.vehicles?.get(i)?.total_no!! > 0){
//                                fetchVehicleResult.vehicles?.get(i)?.total_no = fetchVehicleResult.vehicles?.get(i)?.total_no!! - 1
//
//                                val radioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
//                                val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
//                                selectedVehicles[0] =
//                                    fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
//
//                                validVehicle = true
//                            }
//                    }
//                    if (!validVehicle) {
//                        Toast.makeText(requireContext(), "Vehicle use Maxed", Toast.LENGTH_LONG).show()
//                    }
//
//
//                })

                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
                    selectedVehicles[0] =
                        fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
                }

            }
        }
    }


    planet2.adapter = adapter2
    planet2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        @SuppressLint("SetTextI18n", "CutPasteId")
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            idid: Long
        ) {
            if (isFirstSelection2) {
                isFirstSelection2 = false
                return
            } else {
                val item = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()

                selectedPlants[1] = item
//                    selectedPlants.add(item)

                val radioGroup1 = rootView.findViewById<RadioGroup>(R.id.planet1_radio_group)
                radioGroup1.visibility = View.GONE
                val radioGroup3 = rootView.findViewById<RadioGroup>(R.id.planet3_radio_group)
                radioGroup3.visibility = View.GONE
                val radioGroup4 = rootView.findViewById<RadioGroup>(R.id.planet4_radio_group)
                radioGroup4.visibility = View.GONE

                // Example for setting up radio buttons for the first planet
                val radioGroup = rootView.findViewById<RadioGroup>(R.id.planet2_radio_group)
                radioGroup.removeAllViews() // Clear previous radio buttons
                radioGroup.visibility = View.VISIBLE // Make the RadioGroup visible

                fetchVehicleResult.vehicles?.forEachIndexed { index, vehicle ->
                    val radioButton = RadioButton(requireContext()).apply {
                        text = "${vehicle.name} (${vehicle.total_no})"
                        id = View.generateViewId()
                        tag = index // Use the vehicle's index as the tag for identification
                    }
                    radioGroup.addView(radioButton)
                }

                // Listen for changes in radio button selection
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
                    selectedVehicles[1] =
                        fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
//                        selectedVehicles.add(fetchResult.vehicles?.get(vehicleIndex!!)?.name.toString())
                }

//                radioGroup.setOnClickListener(View.OnClickListener {
//
//                    // decrease counting by one of fetchVehicleResult.vehicles[0].total_no
//                    var validVehicle = false
//                    for (i in 0 until fetchVehicleResult.vehicles!!.size) {
//                        if (fetchVehicleResult.vehicles?.get(i)?.total_no!! > 0) {
//                            fetchVehicleResult.vehicles?.get(i)?.total_no =
//                                fetchVehicleResult.vehicles?.get(i)?.total_no!! - 1
//
//                            val radioButton =
//                                radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
//                            val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
//                            selectedVehicles[1] =
//                                fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
//
//                            validVehicle = true
//                        }
//                    }
//                    if (!validVehicle) {
//                        Toast.makeText(requireContext(), "Vehicle use Maxed", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                })
            }
        }
    }

    planet3.adapter = adapter3
    planet3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        @SuppressLint("SetTextI18n", "CutPasteId")
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            idid: Long
        ) {
            if (isFirstSelection3) {
                isFirstSelection3 = false
                return
            } else {
                val item = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()

                selectedPlants[2] = item
//                    selectedPlants.add(item)


                val radioGroup1 = rootView.findViewById<RadioGroup>(R.id.planet1_radio_group)
                radioGroup1.visibility = View.GONE
                val radioGroup2 = rootView.findViewById<RadioGroup>(R.id.planet2_radio_group)
                radioGroup2.visibility = View.GONE
                val radioGroup4 = rootView.findViewById<RadioGroup>(R.id.planet4_radio_group)
                radioGroup4.visibility = View.GONE


                // Example for setting up radio buttons for the first planet
                val radioGroup = rootView.findViewById<RadioGroup>(R.id.planet3_radio_group)
                radioGroup.removeAllViews() // Clear previous radio buttons
                radioGroup.visibility = View.VISIBLE // Make the RadioGroup visible

                fetchVehicleResult.vehicles?.forEachIndexed { index, vehicle ->
                    val radioButton = RadioButton(requireContext()).apply {
                        text = "${vehicle.name} (${vehicle.total_no})"
                        id = View.generateViewId()
                        tag = index // Use the vehicle's index as the tag for identification
                    }
                    radioGroup.addView(radioButton)
                }

                // Listen for changes in radio button selection
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
                    selectedVehicles[2] =
                        fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
//                        selectedVehicles.add(fetchResult.vehicles?.get(vehicleIndex!!)?.name.toString())
                }

//                radioGroup.setOnClickListener(View.OnClickListener {
//
//                    // decrease counting by one of fetchVehicleResult.vehicles[0].total_no
//                    var validVehicle = false
//                    for (i in 0 until fetchVehicleResult.vehicles!!.size) {
//                        if (fetchVehicleResult.vehicles?.get(i)?.total_no!! > 0) {
//                            fetchVehicleResult.vehicles?.get(i)?.total_no =
//                                fetchVehicleResult.vehicles?.get(i)?.total_no!! - 1
//
//                            val radioButton =
//                                radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
//                            val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
//                            selectedVehicles[2] =
//                                fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
//
//                            validVehicle = true
//                        }
//                    }
//                    if (!validVehicle) {
//                        Toast.makeText(requireContext(), "Vehicle use Maxed", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                })

            }
        }
    }

    planet4.adapter = adapter4
    planet4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        @SuppressLint("CutPasteId", "SetTextI18n")
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            idid: Long
        ) {
            if (isFirstSelection4) {
                isFirstSelection4 = false
                return
            } else {
                val item = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $item", Toast.LENGTH_LONG).show()

                selectedPlants[3] = item
//                    selectedPlants.add(item)

                val radioGroup1 = rootView.findViewById<RadioGroup>(R.id.planet1_radio_group)
                radioGroup1.visibility = View.GONE
                val radioGroup2 = rootView.findViewById<RadioGroup>(R.id.planet2_radio_group)
                radioGroup2.visibility = View.GONE
                val radioGroup3 = rootView.findViewById<RadioGroup>(R.id.planet3_radio_group)
                radioGroup3.visibility = View.GONE


                // Example for setting up radio buttons for the first planet
                val radioGroup = rootView.findViewById<RadioGroup>(R.id.planet4_radio_group)
                radioGroup.removeAllViews() // Clear previous radio buttons
                radioGroup.visibility = View.VISIBLE // Make the RadioGroup visible

                fetchVehicleResult.vehicles?.forEachIndexed { index, vehicle ->
                    val radioButton = RadioButton(requireContext()).apply {
                        text = "${vehicle.name} (${vehicle.total_no})"
                        id = View.generateViewId()
                        tag = index // Use the vehicle's index as the tag for identification
                    }
                    radioGroup.addView(radioButton)
                }

                // Listen for changes in radio button selection
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
                    selectedVehicles[3] =
                        fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1

//                        selectedVehicles.add(fetchResult.vehicles?.get(vehicleIndex!!)?.name.toString())
                }
//                radioGroup.setOnClickListener(View.OnClickListener {
//
//                    // decrease counting by one of fetchVehicleResult.vehicles[0].total_no
//                    var validVehicle = false
//                    for (i in 0 until fetchVehicleResult.vehicles!!.size) {
//                        if (fetchVehicleResult.vehicles?.get(i)?.total_no!! > 0) {
//                            fetchVehicleResult.vehicles?.get(i)?.total_no =
//                                fetchVehicleResult.vehicles?.get(i)?.total_no!! - 1
//
//                            val radioButton =
//                                radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
//                            val vehicleIndex = radioButton.tag as? Int // Retrieve the vehicle index
//                            selectedVehicles[3] =
//                                fetchVehicleResult.vehicles?.get(vehicleIndex!!)?.name.toString() // Update the selected vehicle for planet 1
//
//                            validVehicle = true
//                        }
//                    }
//                    if (!validVehicle) {
//                        Toast.makeText(requireContext(), "Vehicle use Maxed", Toast.LENGTH_LONG)
//                            .show()
//                        radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).isChecked = false
//
//                    }
//                })

            }
        }
    }
}





        // Find the button by its ID and set up the click listener
        val buttonToFinalFragment: Button = rootView.findViewById(R.id.buttonToFinalFragment)

        buttonToFinalFragment.setOnClickListener {


            val result = findFalconeAPI(tokenId, selectedPlants, selectedVehicles)
            // api is not working currently, so making result by own

            // sample response
            val responseStatus="success"
            val planetName = selectedPlants.random()

            if (responseStatus == "success") {
                Toast.makeText(requireContext(), "Planet found: $planetName", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Planet not found", Toast.LENGTH_LONG)
                    .show()
            }


            // find the distance
            fetchPlanetsResult.planets?.forEach {
                if (it.name == planetName) {
                    val distance = it.distance
                    Toast.makeText(requireContext(), "Distance: ${distance.toString()}", Toast.LENGTH_LONG)
                        .show()
                }
            }

            // redirect to final fragment
//            val finalFragment = FinalFragment()
//            requireActivity().supportFragmentManager.beginTransaction().apply {
//                replace(R.id.fragment_container, finalFragment)
//                addToBackStack(null)
//                commit()
//            }
            val finalFragment = FinalFragment().apply {
                // Create a bundle to hold the arguments
                val args = Bundle().apply {
                    putString("planetName", planetName) // Use the actual variable or value here
                    putInt("distance", 100) // Use the actual variable or value here
                }
                // Set the arguments on the fragment
                arguments = args
            }

            // Now perform the fragment transaction as before
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, finalFragment)
                addToBackStack(null) // Optional: Add this transaction to the back stack
                commit()
            }



        //            if (result.toString() == "API error") {
//                Toast.makeText(requireContext(), "API error occured", Toast.LENGTH_LONG)
//                    .show()
//            } else {
//                Toast.makeText(requireContext(), "result: "+result.toString(), Toast.LENGTH_LONG)
//                    .show()
//                // ye kam nai kar rha
//            }

            // todo: handle the result. possible results
            // sample response {"status":"success","planet_name":"Enchai"} OR
            // {"status":"false"} OR
            // {"error":"Some error message"}

        }

        return rootView
    }

    private fun fetchVehicles(): Deferred<FetchResult> = CoroutineScope(Dispatchers.IO).async {
        val url = URL("https://findfalcone.geektrust.com/vehicles")

        (url.openConnection() as? HttpURLConnection)?.run {
            try {
                connect()
                val inputStream = inputStream
                val response = inputStream.bufferedReader().use { it.readText() }
                Log.i("FetchVehicles", response)

                val jsonResponse = JSONArray(response)
                val vehicles = mutableListOf<Vehicle>()
                for (i in 0 until jsonResponse.length()) {
                    val jsonObject = jsonResponse.getJSONObject(i)
                    val vehicle = Vehicle(
                        name = jsonObject.getString("name"),
                        total_no = jsonObject.getInt("total_no"),
                        max_distance = jsonObject.getInt("max_distance"),
                        speed = jsonObject.getInt("speed")
                    )
                    vehicles.add(vehicle)
                }

                return@async FetchResult(success = true, vehicles = vehicles)
            } catch (e: Exception) {
                Log.e("FetchVehicles", "Error fetching vehicles", e)
                return@async FetchResult(success = false, errorMessage = e.message)
            } finally {
                disconnect()
            }
        }
        return@async FetchResult(success = false, errorMessage = "Unable to connect")
    }

    // fetch planets from https://findfalcone.geektrust.com/planets
    // sampleResponse: [{"name":"Donlon","distance":100},{"name":"Enchai","distance":200},{"name":"Jebing","distance":300},{"name":"Sapir","distance":400},{"name":"Lerbin","distance":500},{"name":"Pingasor","distance":600}]

    private fun fetchPlanets(): Deferred<PlanetsFetchResult> = CoroutineScope(Dispatchers.IO).async {
        val url = URL("https://findfalcone.geektrust.com/planets")

        (url.openConnection() as? HttpURLConnection)?.run {
            try {
                connect()
                val inputStream = inputStream
                val response = inputStream.bufferedReader().use { it.readText() }
                Log.i("FetchPlanets", response)

                val jsonResponse = JSONArray(response)
                val planets = mutableListOf<Planet>()
                for (i in 0 until jsonResponse.length()) {
                    val jsonObject = jsonResponse.getJSONObject(i)
                    val planet = Planet(
                        name = jsonObject.getString("name"),
                        distance = jsonObject.getInt("distance")
                    )
                    planets.add(planet)
                }

                return@async PlanetsFetchResult(success = true, planets = planets)
            } catch (e: Exception) {
                Log.e("FetchPlanets", "Error fetching planets", e)
                return@async PlanetsFetchResult(success = false, errorMessage = e.message)
            } finally {
                disconnect()
            }
        }
        return@async PlanetsFetchResult(success = false, errorMessage = "Unable to connect")
    }

    // fetch token POST: https://findfalcone.geektrust.com/token
    // sample res: {"token":"tokenId"}

    private fun fetchToken(): Deferred<String> = CoroutineScope(Dispatchers.IO).async {
        val url = URL("https://findfalcone.geektrust.com/token")
        (url.openConnection() as? HttpURLConnection)?.run {
            try {
                connect()
                val inputStream = inputStream
                val response = inputStream.bufferedReader().use { it.readText() }
                Log.i("FetchToken", response)
                return@async response
            } catch (e: Exception) {
                Log.e("FetchToken", "Error fetching token", e)
                return@async "Error fetching token"
            } finally {
                disconnect()
            }
        }
        return@async "Unable to connect"
    }

    // POST: https://findfalcone.geektrust.com/find
    // sample request body {"token":"tokenId","planet_names":["Donlon","Enchai","Jebing","Sapir"],"vehicle_names":["Space pod","Space rocket","Space shuttle","Space ship"]}
    // sample response {"status":"success","planet_name":"Enchai"} OR
    // {"status":"false"} OR
    // {"error":"Some error message"}

    private fun findFalconeAPI(token: String, planetNames: List<String>, vehicleNames: List<String>): Deferred<String> = CoroutineScope(Dispatchers.IO).async {
        val url = URL("https://findfalcone.geektrust.com/find")
        Log.i("FindFalcone", "Token: $token, Planet names: $planetNames, Vehicle names: $vehicleNames")

        (url.openConnection() as? HttpURLConnection)?.run {
            try {
                connect()
                val outputStream = outputStream
                val requestBody = buildString {
                    append("{")
                    append("\"token\":\"$token\",")
                    append("\"planet_names\":[")
                    planetNames.forEachIndexed { index, planetName ->
                        append("\"$planetName\"")
                        if (index != planetNames.size - 1) {
                            append(",")
                        }
                    }
                    append("],")
                    append("\"vehicle_names\":[")
                    vehicleNames.forEachIndexed { index, vehicleName ->
                        append("\"$vehicleName\"")
                        if (index != vehicleNames.size - 1) {
                            append(",")
                        }
                    }
                    append("]")
                    append("}")
                }
                outputStream.write(requestBody.toByteArray())
                outputStream.flush()

                val inputStream = inputStream
                val response = inputStream.bufferedReader().use { it.readText() }
                Log.i("FindFalcone", response)
                return@async response
            } catch (e: Exception) {
                Log.e("FindFalcone", "Error finding falcone", e)
                return@async "API error"
            } finally {
                disconnect()
            }
        }
        return@async "API error"
    }

}

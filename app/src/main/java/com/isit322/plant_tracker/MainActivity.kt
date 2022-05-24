package com.isit322.plant_tracker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.isit322.artworklist.adapters.AdapterRecycler
import com.isit322.artworklist.data.PlantItem
import com.isit322.artworklist.ui.PlantViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: PlantViewModel
    lateinit var adapterRecyclerView: AdapterRecycler
    var plantList: List<PlantItem>? = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                //precise location access granted.
            }
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                //only approximate location access granted.
            }
            else -> {
                //No location access granted
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PlantViewModel::class.java)
        adapterRecyclerView = AdapterRecycler(plantList, this)
        recycler_view.adapter = adapterRecyclerView
        recycler_view.layoutManager = LinearLayoutManager(this)

        val tempPlantList = arrayListOf(PlantItem("sunflower", "sunflower.png", "23213"),
            PlantItem("blueberries", "bluerries.png", "11111")
        )

        val plantList: ArrayList<PlantItem> = ArrayList()
        plantList.add(PlantItem("sunflower", "sunflower.png", "23232"))
        plantList.add(PlantItem("blueberry", "blurberrie.png", "11111"))

        /*
            locationPermissionRequest.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION))
        */

        val startButton = findViewById<Button>(R.id.StartBtn)
        startButton.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            viewModel.getArtwork(this)
            viewModel.plantResponse.observe(this) {
                if (!it.isEmpty()) {
                    progress_bar.visibility = View.GONE
                    linear_layout_recycler_view.visibility = View.VISIBLE
                    adapterRecyclerView.setData(plantList)
                } else {
                    progress_bar.visibility = View.GONE
                }
            }
            val intent = Intent(this, MapActivity::class.java)
            intent.putParcelableArrayListExtra("plantData", plantList)
            startActivity(intent)
        }

        val enterPlantButton = findViewById<Button>(R.id.settingsBtn)
        enterPlantButton.setOnClickListener {
            val intent = Intent(this, UserPlantInput::class.java)
            startActivity(intent)
        }

    }
}

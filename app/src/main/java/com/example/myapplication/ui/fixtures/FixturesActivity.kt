package com.example.myapplication.ui.fixtures

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.example.myapplication.retrofit.CricketRepository
import com.example.myapplication.retrofit.RetrofitClient
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.myapplication.R
import com.example.myapplication.datamodels.Fixtures

class FixturesActivity : ComponentActivity() {

    private val repository = CricketRepository(RetrofitClient.getApiService())
    private val viewModel : MainActivityViewModel by viewModels { MainActivityViewModelFactory(repository) }
    private lateinit var recyclerView : WearableRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fixtures)

        viewModel.fixturesLiveDate.observe(this, Observer {
            setListView(it)
        })

        recyclerView = findViewById<WearableRecyclerView>(R.id.recycler_view)

        recyclerView.apply {
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(this@FixturesActivity)
        }
    }

    private fun setListView(fixtures : Fixtures){
        val adapter = FixturesAdapter(fixtures.results)
        recyclerView.adapter = adapter
    }
}

/** How much should we scale the icon at most.  */
private const val MAX_ICON_PROGRESS = 0.25f

class CustomScrollingLayoutCallback : WearableLinearLayoutManager.LayoutCallback() {

    private var progressToCenter: Float = 0f

    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        child.apply {
            // Figure out % progress from top to bottom
            val centerOffset = height.toFloat() / 2.0f / parent.height.toFloat()
            val yRelativeToCenterOffset = y / parent.height + centerOffset

            // Normalize for center
            progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            progressToCenter = Math.min(progressToCenter, MAX_ICON_PROGRESS)

            scaleX = 1 - progressToCenter
            scaleY = 1 - progressToCenter
        }
    }
}
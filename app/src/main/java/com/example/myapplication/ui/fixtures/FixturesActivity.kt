package com.example.myapplication.ui.fixtures

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import com.example.myapplication.retrofit.CricketRepository
import com.example.myapplication.retrofit.RetrofitClient
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.datamodels.Fixtures

class FixturesActivity : ComponentActivity() {

    private val repository = CricketRepository(RetrofitClient.getApiService())
    private val viewModel : FixturesActivityViewModel by viewModels { FixturesActivityViewModelFactory(repository) }
    private lateinit var recyclerView : WearableRecyclerView
    private lateinit var loading : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fixtures)

        loading = findViewById(R.id.loading)

        viewModel.fixturesLiveDate.observe(this, Observer {
            loading.visibility = View.GONE
            setListView(it)
        })

        recyclerView = findViewById<WearableRecyclerView>(R.id.recycler_view)

        recyclerView.apply {
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(this@FixturesActivity)
        }
    }

    private fun setListView(fixtures : List<Fixtures.Result>){
        val adapter = FixturesAdapter(fixtures) {
            val intent = Intent()
            intent.putExtra(Constants.MATCH_ID,it.id)
            intent.putExtra(Constants.MATCH,it)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
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